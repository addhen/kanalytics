// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.android

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.addhen.kanalytics.sample.shared.SampleApp

class MainActivity : ComponentActivity() {

  private val requestPermissionLauncher =
    registerForActivityResult(
      ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
      if (isGranted) {
        // Permission granted, proceed with notification setup
      } else {
        // Permission denied, handle accordingly
        if (ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.POST_NOTIFICATIONS,
          )
        ) {
          // User denied permission, show rationale and request again
          showPermissionRationaleDialog()
        } else {
          // User denied permission and selected "Don't ask again"
          // Guide the user to app settings
          showAppSettingsDialog()
        }
      }
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    setContent {
      SampleApp()
    }
  }

  override fun onResume() {
    super.onResume()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      requestNotificationPermission()
    }
  }

  private fun requestNotificationPermission() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
      != PackageManager.PERMISSION_GRANTED
    ) {
      // Permission not granted, request it
      requestPermissionLauncher.launch((Manifest.permission.POST_NOTIFICATIONS))
    } else {
      // Permission already granted, proceed with notification setup
    }
  }

  private fun showPermissionRationaleDialog() {
    // Show a dialog explaining why the permission is needed
    // and provide an option to request it again
    // ... (Implementation for your dialog) ...
  }

  private fun showAppSettingsDialog() {
    // Show a dialog explaining that the permission is needed
    // and guide the user to app settings to grant it manually
    // ... (Implementation for your dialog) ...

    // Example:
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri: Uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
  }
}
