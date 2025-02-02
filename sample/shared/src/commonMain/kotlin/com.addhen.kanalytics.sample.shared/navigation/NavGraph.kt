// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.shared.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlin.reflect.KClass

@Composable
public fun AppNavGraph(
  navController: NavHostController,
  startDestination: KClass<*>,
  sampleScreen: @Composable () -> Unit,
) {
  NavHost(
    navController = navController,
    startDestination = startDestination,
  ) {
    composable<HomeRoute> {
      sampleScreen()
    }
  }
}
