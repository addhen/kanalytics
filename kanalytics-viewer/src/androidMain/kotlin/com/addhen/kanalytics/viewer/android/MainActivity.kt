// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.android

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.addhen.kanalytics.viewer.app.shared.ui.ViewerApp
import dev.icerock.moko.permissions.PermissionsController

public class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    // Navigation icon color can be changed since API 26(O)
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      enableEdgeToEdge()
    } else {
      enableEdgeToEdge(
        statusBarStyle = SystemBarStyle.auto(
          lightScrim = Color.Transparent.toArgb(),
          darkScrim = Color.Transparent.toArgb(),
        ),
        navigationBarStyle = SystemBarStyle.auto(
          lightScrim = Color.Transparent.toArgb(),
          darkScrim = Color.Transparent.toArgb(),
        ),
      )

      // For API29(Q) or higher and 3-button navigation,
      // the following code must be written to make the navigation color completely transparent.
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        window.isNavigationBarContrastEnforced = false
      }
    }
    super.onCreate(savedInstanceState)
    val permissionsController = PermissionsController(this)
    permissionsController.bind(this)
    setContent { ViewerApp() }
  }
}
