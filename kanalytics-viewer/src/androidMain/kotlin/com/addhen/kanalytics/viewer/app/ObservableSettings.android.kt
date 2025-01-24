// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app

import androidx.preference.PreferenceManager
import com.addhen.kanalytics.viewer.app.android.ContextInitializer
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings

internal actual fun provideObservableSettings(): ObservableSettings {
  val sharedPref = PreferenceManager
    .getDefaultSharedPreferences(ContextInitializer.applicationContext)

  return SharedPreferencesSettings(sharedPref)
}
