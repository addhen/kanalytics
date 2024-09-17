// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.shared.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import kotlin.reflect.KClass

@Composable
public fun AppNavGraph(
  navController: NavHostController,
  startDestination: KClass<*>,
  locationScreen: @Composable () -> Unit,
) {
  NavHost(
    navController = navController,
    startDestination = startDestination,
  ) {

    composable<LocationRoute> {
      locationScreen()
    }
  }
}

public fun NavController.buildNavOptions(): NavOptions = navOptions {
  // Pop up to the start destination of the graph to
  // avoid building up a large stack of destinations
  // on the back stack as users select items
  // Fixes an issue with the back button causing the weather screen to be relaunched
  // after attempting to navigate back to the map screen.
  popUpTo(graph.findStartDestination().id) { saveState = true }
  // Restore state when re-selecting a previously selected item
  restoreState = true
}
