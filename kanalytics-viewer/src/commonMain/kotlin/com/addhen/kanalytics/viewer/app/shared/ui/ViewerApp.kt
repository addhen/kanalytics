// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.addhen.kanalytics.viewer.app.shared.ui.component.AppSurface
import com.addhen.kanalytics.viewer.app.shared.ui.navigation.AppNavGraph
import com.addhen.kanalytics.viewer.app.shared.ui.navigation.EventViewerRoute
import com.addhen.kanalytics.viewer.app.shared.ui.settings.DefaultViewerAppPreference
import com.addhen.kanalytics.viewer.app.shared.ui.theme.AppTheme
import com.addhen.kanalytics.viewer.disposeViewerAppWindow

@Composable
public fun ViewerApp(navController: NavHostController = rememberNavController()) {
  DisposableEffect(Unit) {
    onDispose {
      disposeViewerAppWindow()
    }
  }

  CompositionLocalProvider(
    LocalPreferences provides DefaultViewerAppPreference.Instance,
  ) {
    AppTheme {
      AppSurface {
        AppNavGraph(
          navController = navController,
          EventViewerRoute::class,
        )
      }
    }
  }
}
