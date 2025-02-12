// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.base.profile

import android.Manifest
import android.os.Build
import androidx.test.uiautomator.UiDevice

fun UiDevice.allowNotifications(packageName: String) {
  if (Build.VERSION.SDK_INT >= 33) {
    executeShellCommand("pm grant $packageName ${Manifest.permission.POST_NOTIFICATIONS}")
  }
}
