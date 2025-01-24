// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.settings

public interface ViewerAppPreferences {

  public val theme: Preference<Theme>
  public val useDynamicColors: Preference<Boolean>

  public val dataRetentionDays: Preference<Int>

  public enum class Theme {
    LIGHT,
    DARK,
    SYSTEM,
  }
}
