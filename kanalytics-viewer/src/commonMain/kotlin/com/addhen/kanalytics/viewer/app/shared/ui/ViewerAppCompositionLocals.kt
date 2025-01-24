package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.addhen.kanalytics.viewer.app.shared.ui.settings.ViewerAppPreferences

internal val LocalPreferences = staticCompositionLocalOf<ViewerAppPreferences> {
  error("LocalPreferences not provided")
}
