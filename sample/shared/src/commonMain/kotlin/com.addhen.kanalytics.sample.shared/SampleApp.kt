// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.shared

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.addhen.kanalytics.sample.shared.component.AppSurface
import com.addhen.kanalytics.sample.shared.navigation.AppNavGraph
import com.addhen.kanalytics.sample.shared.navigation.HomeRoute

@Composable
public fun SampleApp(navController: NavHostController, locationScreen: @Composable () -> Unit) {
  SamplesTheme {
    AppSurface {
      AppNavGraph(
        navController = navController,
        HomeRoute::class,
        sampleScreen = locationScreen,
      )
    }
  }
}
