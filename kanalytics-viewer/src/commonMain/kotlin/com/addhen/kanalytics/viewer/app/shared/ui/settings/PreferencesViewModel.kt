package com.addhen.kanalytics.viewer.app.shared.ui.settings

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class PreferencesViewModel(
  preferences: ViewerAppPreferences
): ViewModel() {

  private val defaultPreferences by lazy { preferences }

  private val theme = defaultPreferences.theme.flow
  private val useDynamicColors = defaultPreferences.useDynamicColors.flow

  val viewState = combine(
    theme,
    useDynamicColors,
    { theme: ViewerAppPreferences.Theme, useDynamicColors: Boolean ->
      PreferencesUiState(theme = theme, useDynamicColors = useDynamicColors)
    }
  ).stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = PreferencesUiState(
      theme = ViewerAppPreferences.Theme.SYSTEM,
      useDynamicColors = true,
    )
  )

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

  @Stable
  data class PreferencesUiState(
    val theme: ViewerAppPreferences.Theme,
    val useDynamicColors: Boolean,
  )

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
