// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.settings

import com.addhen.kanalytics.viewer.app.provideObservableSettings
import com.addhen.kanalytics.viewer.app.shared.AppCoroutineDispatchers
import com.addhen.kanalytics.viewer.app.shared.applicationCoroutineScope
import com.addhen.kanalytics.viewer.app.shared.ui.settings.ViewerAppPreferences.Theme
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import com.russhwolf.settings.set
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext

internal const val THEME_LIGHT_VALUE = "light"
internal const val THEME_DARK_VALUE = "dark"
internal const val THEME_SYSTEM_VALUE = "system"
internal const val KEY_THEME = "pref_theme"
internal const val KEY_USE_DYNAMIC_COLORS = "pref_dynamic_colors"
internal const val KEY_RENTION_DAYS = "pref_data_retention_days"

@OptIn(ExperimentalSettingsApi::class)
internal class DefaultViewerAppPreference(
  settings: ObservableSettings,
  private val coroutineScope: CoroutineScope,
  private val dispatchers: AppCoroutineDispatchers,
) : ViewerAppPreferences {

  private val settings: ObservableSettings by lazy { settings }
  private val flowSettings by lazy { settings.toFlowSettings(dispatchers.io) }

  override val theme: Preference<Theme> by lazy {
    MappingPreference(KEY_THEME, Theme.SYSTEM, ::getThemeForStorageValue, ::themeToStorageValue)
  }

  override val useDynamicColors: Preference<Boolean> by lazy {
    BooleanPreference(KEY_USE_DYNAMIC_COLORS, true)
  }

  override val dataRetentionDays: Preference<Int> by lazy {
    IntPreference(KEY_RENTION_DAYS, 7)
  }

  private inner class BooleanPreference(
    private val key: String,
    override val defaultValue: Boolean = false,
  ) : Preference<Boolean> {
    override suspend fun set(value: Boolean) = withContext(dispatchers.io) {
      settings[key] = value
    }

    override suspend fun get(): Boolean = withContext(dispatchers.io) {
      settings.getBoolean(key, defaultValue)
    }

    override val flow: StateFlow<Boolean> by lazy {
      flowSettings
        .getBooleanFlow(key, defaultValue)
        .stateIn(
          scope = coroutineScope,
          started = SharingStarted.WhileSubscribed(SUBSCRIBED_TIMEOUT),
          initialValue = defaultValue,
        )
    }
  }

  private inner class IntPreference(
    private val key: String,
    override val defaultValue: Int = 0,
  ) : Preference<Int> {
    override suspend fun set(value: Int) = withContext(dispatchers.io) {
      settings[key] = value
    }

    override suspend fun get(): Int = withContext(dispatchers.io) {
      settings.getInt(key, defaultValue)
    }

    override val flow: StateFlow<Int> by lazy {
      flowSettings
        .getIntFlow(key, defaultValue)
        .stateIn(
          scope = coroutineScope,
          started = SharingStarted.WhileSubscribed(SUBSCRIBED_TIMEOUT),
          initialValue = defaultValue,
        )
    }
  }

  private inner class MappingPreference<V>(
    private val key: String,
    override val defaultValue: V,
    private val toValue: (String) -> V,
    private val fromValue: (V) -> String,
  ) : Preference<V> {
    override suspend fun set(value: V) = withContext(dispatchers.io) {
      settings[key] = fromValue(value)
    }

    override suspend fun get(): V = withContext(dispatchers.io) {
      settings.getStringOrNull(key)?.let(toValue) ?: defaultValue
    }

    override val flow: Flow<V> by lazy {
      flowSettings.getStringOrNullFlow(key)
        .map { it?.let(toValue) ?: defaultValue }
        .shareIn(
          scope = coroutineScope,
          started = SharingStarted.WhileSubscribed(SUBSCRIBED_TIMEOUT),
        )
    }
  }

  internal companion object {
    val SUBSCRIBED_TIMEOUT = 20.seconds

    val Instance: ViewerAppPreferences by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
      DefaultViewerAppPreference(
        provideObservableSettings(),
        applicationCoroutineScope,
        AppCoroutineDispatchers.Instance,
      )
    }
  }
}

private fun themeToStorageValue(theme: Theme): String = when (theme) {
  Theme.LIGHT -> THEME_LIGHT_VALUE
  Theme.DARK -> THEME_DARK_VALUE
  Theme.SYSTEM -> THEME_SYSTEM_VALUE
}

private fun getThemeForStorageValue(value: String) = when (value) {
  THEME_LIGHT_VALUE -> Theme.LIGHT
  THEME_DARK_VALUE -> Theme.DARK
  else -> Theme.SYSTEM
}

private fun ObservableSettings.toggleBoolean(key: String, defaultValue: Boolean = false) {
  putBoolean(key, !getBoolean(key, defaultValue))
}
