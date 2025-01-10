// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.addhen.kanalytics.KAnalytics
import com.addhen.kanalytics.KAnalyticsEvent
import kotlin.random.Random
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

public class SampleViewModel(
  private val kanalytics: KAnalytics,
) : ViewModel() {
  private val viewStateEmitter =
    MutableStateFlow(LocationUiState(flag = LocationUiState.Flag.LOADING))
  private val actionStateFlow = MutableSharedFlow<UiAction>()

  public val viewState: StateFlow<LocationUiState> = viewStateEmitter
    .stateIn(
      viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      viewStateEmitter.value,
    )

  public val action: (UiAction) -> Unit = { action ->
    viewModelScope.launch { actionStateFlow.emit(action) }
  }

  init {
    actionStateFlow
      .onStart { emit(UiAction.LoadEvent) }
      .map { action ->
        when (action) {
          is UiAction.TriggerAnalyticsEvent -> sendAnalyticsEvent()
          is UiAction.LoadEvent -> {
            // do nothing
          }
        }

      }
      .onEach {
        viewStateEmitter.update { it.copy(flag = LocationUiState.Flag.IDLE) }
    }.launchIn(viewModelScope)
  }

  public fun sendAnalyticsEvent() {
    val event = KAnalyticsEvent("EventName ${generateRandomString()}").apply {
      addParameter("key ${generateRandomString()}", "value ${generateRandomString()}")
    }
    kanalytics.send(event)
  }

  public sealed interface UiAction {
    public data object TriggerAnalyticsEvent: UiAction
    public data object LoadEvent: UiAction
  }

  public data class LocationUiState(
    public val state: String = "",
    public val flag: Flag = Flag.IDLE,
  ) {
    public enum class Flag {
      LOADING,
      IDLE,
    }
  }

  private fun generateRandomString(): String {
    return Random.nextInt(1000).toString()
  }
}
