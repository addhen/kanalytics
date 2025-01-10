// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.addhen.kanalytics.handleErrorWithRetry
import com.addhen.kanalytics.stateInWhileSubscribed
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.data.repository.EventDataRepository
import com.addhen.kanalytics.viewer.app.shared.ui.component.UiMessageManager
import com.addhen.kanalytics.viewer.app.shared.ui.component.UiMessageStateHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val SEARCH_QUERY: String = "search_query"

@OptIn(ExperimentalCoroutinesApi::class)
internal class EventViewerViewModel(
  private val eventRepository: EventDataRepository,
  private val savedStateHandle: SavedStateHandle,
  internal val uiMessageStateHolder: UiMessageStateHolder
) : ViewModel() {

  private val viewStateEmitter = MutableStateFlow(
    EventViewerUiState(flag = EventViewerUiState.Flag.LOADING)
  )
  private val uiAction = MutableSharedFlow<UiAction>()

  val viewState: StateFlow<EventViewerUiState> = viewStateEmitter
    .stateInWhileSubscribed(viewModelScope, viewStateEmitter.value)

  val action: (UiAction) -> Unit = { action ->
    viewModelScope.launch { uiAction.emit(action) }
  }

  init {
    val initialQuery = savedStateHandle.get<String>(SEARCH_QUERY).orEmpty()
    uiAction
      .onStart {
        val uiAction =
          if (initialQuery.isEmpty()) UiAction.LoadEvents else UiAction.SearchEvents(initialQuery)
        emit(uiAction)
      }
      .transformLatest { action ->
        when (action) {
          is UiAction.SearchEvents -> {
            delay(600) // Debounce search actions
            emit(action)
          }

          else -> emit(action)
        }
      }
      .flatMapLatest {
        when (it) {
          is UiAction.LoadEvents -> {
            // I don't like the use of the pair here. I'm using the pair here
            // so to pass the search query to the view state.
            // I'm not sure if this is the best way to do this.
            eventRepository.getAll().map { events -> events to "" }
          }

          is UiAction.SearchEvents -> {
            // I don't like the use of the pair here. I'm using the pair here
            // so to pass the search query to the view state.
            // I'm not sure if this is the best way to do this.
            eventRepository.search(it.query).map { events -> events to it.query }
          }
        }
      }
      .onEach { (events, searchQuery) ->
        viewStateEmitter.update { currentUiState ->
          currentUiState.copy(
            flag = EventViewerUiState.Flag.IDLE,
            events = events,
            searchQuery = searchQuery,
          )
        }
      }
      .handleErrorWithRetry(uiMessageStateHolder)
      .launchIn(viewModelScope)
  }

  override fun onCleared() {
    savedStateHandle[SEARCH_QUERY] = viewState.value.searchQuery
    super.onCleared()
  }

  sealed interface UiAction {
    data object LoadEvents : UiAction
    data class SearchEvents(val query: String) : UiAction
  }

  @Stable
  data class EventViewerUiState(
    val events: List<EventData> = emptyList(),
    val searchQuery: String = "",
    val flag: Flag = Flag.IDLE,
  ) {
    enum class Flag {
      LOADING,
      IDLE,
    }
  }

  companion object {

    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val savedStateHandle = createSavedStateHandle()
        val repository = EventDataRepository.Instance
        val uiMessageStateHolder = UiMessageManager.Instance
        EventViewerViewModel(
          eventRepository = repository,
          savedStateHandle = savedStateHandle,
          uiMessageStateHolder = uiMessageStateHolder
        )
      }
    }
  }
}
