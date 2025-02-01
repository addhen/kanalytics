// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.eventdetail

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.addhen.kanalytics.viewer.app.handleErrorWithRetry
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.data.repository.EventDataRepository
import com.addhen.kanalytics.viewer.app.shared.ui.component.UiMessageManager
import com.addhen.kanalytics.viewer.app.shared.ui.component.UiMessageStateHolder
import com.addhen.kanalytics.viewer.app.stateInWhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

internal class EventDetailsViewModel(
  private val eventRepository: EventDataRepository,
  internal val uiMessageStateHolder: UiMessageStateHolder,
  private val eventId: Long,
) : ViewModel(), UiMessageStateHolder by uiMessageStateHolder {

  val viewState: StateFlow<EventDetailsUiState> = eventRepository.getEventById(eventId)
    .map { eventDataEntity ->
      EventDetailsUiState(
        event = eventDataEntity,
        flag = EventDetailsUiState.Flag.IDLE,
      )
    }
    .handleErrorWithRetry(uiMessageStateHolder)
    .stateInWhileSubscribed(
      viewModelScope,
      EventDetailsUiState(flag = EventDetailsUiState.Flag.LOADING),
    )

  @Stable
  data class EventDetailsUiState(
    val event: EventData? = null,
    val flag: Flag = Flag.IDLE,
  ) {
    enum class Flag {
      LOADING,
      IDLE,
    }
  }

  companion object {

    fun create(eventId: Long): ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val repository = EventDataRepository.Instance
        val uiMessageStateHolder = UiMessageManager()
        EventDetailsViewModel(
          eventRepository = repository,
          uiMessageStateHolder = uiMessageStateHolder,
          eventId = eventId,
        )
      }
    }
  }
}
