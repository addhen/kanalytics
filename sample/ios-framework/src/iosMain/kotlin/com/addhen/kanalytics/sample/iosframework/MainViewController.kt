// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.iosframework

import androidx.compose.ui.window.ComposeUIViewController
import androidx.navigation.compose.rememberNavController
import com.addhen.kanalytics.sample.shared.SampleApp
import platform.UIKit.UIViewController

@Suppress("standard:function-naming")
public fun mainViewController(): UIViewController = ComposeUIViewController {
  val navController = rememberNavController()

  SampleApp(navController = navController)
}
