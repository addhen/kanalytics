// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.shared

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.addhen.kanalytics.sample.shared.component.AppSurface
import com.addhen.kanalytics.sample.shared.navigation.AppNavGraph
import com.addhen.kanalytics.sample.shared.navigation.HomeRoute
import com.addhen.kanalytics.viewer.app.shared.ui.launchViewerApp

@Composable
public fun SampleApp(
  navController: NavHostController = rememberNavController(),
  onEventViewerTrigger: () -> Unit = { launchViewerApp() },
) {
  SampleTheme {
    AppSurface {
      AppNavGraph(
        navController = navController,
        HomeRoute::class,
        sampleScreen = { SampleScreen(viewModel, onEventViewerTrigger) },
      )
    }
  }
}
