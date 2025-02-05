// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch

internal class PreferencesViewModel(preferences: ViewerAppPreferences) : ViewModel() {

  val defaultPreferences by lazy { preferences }

  val action: (UiAction) -> Unit = { action ->
    viewModelScope.launch {
      when (action) {
        is UiAction.SaveTheme -> {
          Logger.d { "Saving theme: $action" }
          defaultPreferences.theme.set(action.theme)
        }

        UiAction.UseDynamicColors -> {
          Logger.d { "Use dynamic colors" }
          defaultPreferences.useDynamicColors.toggle()
        }
      }
    }
  }

  sealed interface UiAction {
    data class SaveTheme(val theme: ViewerAppPreferences.Theme) : UiAction
    data object UseDynamicColors : UiAction
  }

  companion object {

    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val preferences = DefaultViewerAppPreference.Instance
        PreferencesViewModel(preferences)
      }
    }
  }
}
