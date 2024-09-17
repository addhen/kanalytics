// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.addhen.kanalytics.viewer.app.shared.ui.component.AppSurface
import com.addhen.kanalytics.viewer.app.shared.ui.navigation.AppNavGraph
import com.addhen.kanalytics.viewer.app.shared.ui.navigation.LocationRoute

@Composable
public fun ViewerApp(
  navController: NavHostController,
  permissionScreen: @Composable () -> Unit,
  locationScreen: @Composable () -> Unit,
) {
  ViewerAppTheme {
    AppSurface {
      AppNavGraph(
        navController = navController,
        LocationRoute::class,
        permissionScreen = permissionScreen,
        locationScreen = locationScreen,
      )
    }
  }
}
