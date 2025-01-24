// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.addhen.kanalytics.viewer.app.shared.ui.settings.ViewerAppPreferences

internal val LocalPreferences = staticCompositionLocalOf<ViewerAppPreferences> {
  error("LocalPreferences not provided")
}
