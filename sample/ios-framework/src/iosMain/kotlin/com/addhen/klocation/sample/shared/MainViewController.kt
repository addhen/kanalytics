// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.klocation.sample.shared

import androidx.compose.ui.window.ComposeUIViewController
import androidx.navigation.compose.rememberNavController
import com.addhen.klocation.LocationService
import com.addhen.klocation.cllocation
import com.addhen.klocation.sample.shared.permission.LocationPermissionScreen
import com.addhen.klocation.sample.shared.permission.LocationPermissionViewModel
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.ios.PermissionsController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIViewController

@OptIn(ExperimentalForeignApi::class)
@Suppress("standard:function-naming")
public fun mainViewController(): UIViewController = ComposeUIViewController {
  val navController = rememberNavController()
  SampleApp(
    navController = navController,
    permissionsController = PermissionsController(),
    permissionScreen = {
      val viewModel = LocationPermissionViewModel(
        PermissionsController(),
        Permission.LOCATION,
      )
      LocationPermissionScreen(viewModel, navController)
    },
    locationScreen = {
      val locationService = LocationService()
      val viewModel = LocationViewModel(LocationService())
      LocationScreen(viewModel, locationService) { locationState ->
        val location = locationState.cllocation
        location?.coordinate?.useContents {
          "$latitude,$longitude"
        } ?: ""
      }
    },
  )
}
