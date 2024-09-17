// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

public class LocationViewModel() : ViewModel() {
  private val viewStateEmitter =
    MutableStateFlow(LocationUiState(flag = LocationUiState.Flag.LOADING))

  public val viewState: StateFlow<LocationUiState> = viewStateEmitter
    .stateIn(
      viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      viewStateEmitter.value,
    )

  init {
    flow { emit("Hey my location") }
      .distinctUntilChanged()
      .onEach { state ->
        Logger.d(LocationViewModel::class.simpleName.toString()) { "state $state" }
        viewStateEmitter.update { currentUiState ->
          currentUiState.copy(
            flag = LocationUiState.Flag.IDLE,
            state = state,
          )
        }
      }.launchIn(viewModelScope)
  }

  public data class LocationUiState(
      public val state: String = "",
      public val flag: Flag = Flag.IDLE,
  ) {
    public enum class Flag {
      LOADING,
      ERROR,
      IDLE,
    }
  }
}
