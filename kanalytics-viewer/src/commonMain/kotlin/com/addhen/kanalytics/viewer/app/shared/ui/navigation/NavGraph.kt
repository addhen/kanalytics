// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle.State.RESUMED
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.addhen.kanalytics.viewer.app.shared.ui.eventdetail.EventDetailsScreen
import com.addhen.kanalytics.viewer.app.shared.ui.eventlist.EventViewerScreen
import kotlin.reflect.KClass

@Composable
public fun AppNavGraph(navController: NavHostController, startDestination: KClass<*>) {
  NavHost(
    navController = navController,
    startDestination = startDestination,
  ) {
    composable<EventViewerRoute> {
      EventViewerScreen { eventId, eventName ->
        navController.navigate(
          EventDetailsRoute(eventId, eventName),
          navController.buildNavOptions(),
        )
      }
    }

    composable<EventDetailsRoute> { navBackStackEntry ->
      val route = navBackStackEntry.toRoute<EventDetailsRoute>()
      EventDetailsScreen(
        eventId = remember { route.eventId },
        eventName = remember { route.eventName },
      ) {
        // Checking if the current back stack entry is resumed to avoid an issue when the
        // back button is double pressed in succession which causes the display to show a
        // blank screen with no view components on it.
        // See: https://github.com/google/accompanist/issues/1408#issuecomment-1673011548
        if (navController.currentBackStackEntry?.lifecycle?.currentState == RESUMED) {
          navController.popBackStack()
        }
      }
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
