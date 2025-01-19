// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

internal var viewerAppViewControllerInstance: UIViewController? = null

internal fun viewerAppViewController(): UIViewController = ComposeUIViewController(configure = {
  enforceStrictPlistSanityCheck = false
}) {
  ViewerApp()
}.also {
  viewerAppViewControllerInstance = it
}
