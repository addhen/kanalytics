// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.data.repository.EventDataRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
internal class EventViewerViewModel(
  private val eventRepository: EventDataRepository,
) : ViewModel() {

  private val uiAction = MutableSharedFlow<UiAction>()
  private val viewStateEmitter = MutableStateFlow(
    EventViewerUiState(flag = EventViewerUiState.Flag.LOADING)
  )

  val viewState: StateFlow<EventViewerUiState> = viewStateEmitter
    .stateInWhileSubscribed(viewStateEmitter.value)

  init {
    uiAction
      .onStart { emit(UiAction.LoadEvents) }
      .flatMapLatest { processAction(it) }
      .distinctUntilChanged()
      .onEach {
        viewStateEmitter.update { currentUiState ->
          currentUiState.copy(
            flag = EventViewerUiState.Flag.IDLE,
            events = it
          )
        }
      }
      .catch {
        viewStateEmitter.update { currentUiState ->
          currentUiState.copy(flag = EventViewerUiState.Flag.ERROR)
        }
      }
      .launchIn(viewModelScope)
  }

  fun performAction(uiAction: UiAction) {
    when (uiAction) {
      is UiAction.LoadEvents -> loadEvents()
      is UiAction.SearchEvents -> searchEvents(uiAction.query)
    }
  }

  private fun processAction(it: UiAction) = when (it) {
    is UiAction.LoadEvents -> eventRepository.getAll()
    is UiAction.SearchEvents -> {
      // todo: implement search
      flow { emptyList<EventData>() }
      //eventRepository.search(it.query)
    }
  }

  private fun loadEvents() {
    viewModelScope.launch {
      uiAction.emit(UiAction.LoadEvents)
    }
  }

  private fun searchEvents(query: String) {
    viewModelScope.launch {
      uiAction.emit(UiAction.SearchEvents(query))
    }
  }

  sealed interface UiAction {
    data object LoadEvents : UiAction
    data class SearchEvents(val query: String) : UiAction
  }

  data class EventViewerUiState(
    val events: List<EventData> = emptyList(),
    val eventTotalCount: Long = 0,
    val flag: Flag = Flag.IDLE
  ) {
    enum class Flag {
      LOADING,
      ERROR,
      IDLE
    }
  }

  private fun <T> Flow<T>.stateInWhileSubscribed(initialValue: T): StateFlow<T> {
    return stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = initialValue,
    )
  }
}
