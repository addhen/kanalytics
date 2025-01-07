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
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.data.repository.EventDataRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val LAST_SEARCH_QUERY: String = "last_search_query"

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
internal class EventViewerViewModel(
  private val eventRepository: EventDataRepository,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {

  private val viewStateEmitter = MutableStateFlow(
    EventViewerUiState(flag = EventViewerUiState.Flag.LOADING)
  )
  val viewState: StateFlow<EventViewerUiState> = viewStateEmitter
    .stateInWhileSubscribed(viewStateEmitter.value)
  val action: (UiAction) -> Unit
  private val uiAction = MutableSharedFlow<UiAction>()

  init {
    val initialQuery: String = savedStateHandle[LAST_SEARCH_QUERY] ?: ""

    val currentQuery = uiAction
      .filterIsInstance<UiAction.SearchEvents>()
      .debounce(600)
      .distinctUntilChanged()
      .map { it.query }
      .stateInWhileSubscribed(initialValue = initialQuery)

    val searchResults = currentQuery
      .flatMapLatest { query ->
        eventRepository.search(query)
      }

    val loadEvents = uiAction
      .filterIsInstance<UiAction.LoadEvents>()
      .distinctUntilChanged()
      .onStart { emit(UiAction.LoadEvents) }
      .flatMapLatest {
        eventRepository.getAll()
      }

    combine(
      searchResults,
      loadEvents,
      currentQuery,
    ) { search, load, query ->
      val events = if (query.isNotEmpty()) search else load
      EventViewerUiState(
        flag = EventViewerUiState.Flag.IDLE,
        events = events,
        searchQuery = query,
      )
    }.onEach {
      viewStateEmitter.update { currentUiState ->
        currentUiState.copy(
          flag = EventViewerUiState.Flag.IDLE,
          events = it.events,
          searchQuery = it.searchQuery,
        )
      }
    }.catch {
      viewStateEmitter.update { currentUiState ->
        currentUiState.copy(flag = EventViewerUiState.Flag.ERROR)
      }
    }.launchIn(viewModelScope)

    action = { action ->
      viewModelScope.launch {
        uiAction.emit(action)
      }
    }
  }

  override fun onCleared() {
    savedStateHandle[LAST_SEARCH_QUERY] = viewState.value.searchQuery
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
      ERROR,
      IDLE,
    }
  }

  private fun <T> Flow<T>.stateInWhileSubscribed(initialValue: T): StateFlow<T> {
    return stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = initialValue,
    )
  }

  companion object {

    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val savedStateHandle = createSavedStateHandle()
        val repository = EventDataRepository.Instance
        EventViewerViewModel(
          eventRepository = repository,
          savedStateHandle = savedStateHandle,
        )
      }
    }
  }
}
