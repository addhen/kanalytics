// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.addhen.kanalytics.viewer.app.shared.ui.LocationScreen
import com.addhen.kanalytics.viewer.app.shared.ui.LocationViewModel
import com.addhen.kanalytics.viewer.app.shared.ui.ViewerApp
import dev.icerock.moko.permissions.PermissionsController

public class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    val permissionsController = PermissionsController(this)
    permissionsController.bind(this)
    setContent {
      val navController = rememberNavController()
      ViewerApp(
        navController,
        permissionScreen = {},
        locationScreen = {
          val viewModel = LocationViewModelFactory().create(LocationViewModel::class.java)
          LocationScreen(
            viewModel
          )
        },
      )
    }
  }
}
