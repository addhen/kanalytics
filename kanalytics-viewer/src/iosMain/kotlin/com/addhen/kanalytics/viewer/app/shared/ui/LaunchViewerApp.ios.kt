package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIApplication

public actual fun launchViewerApp() {
  val pluginViewController = ComposeUIViewController { ViewerApp() }
  val topController = UIApplication.sharedApplication.keyWindow?.rootViewController
    ?: throw IllegalStateException("No key window or root view controller found")
  topController.presentViewController(pluginViewController, animated = true, completion = null)
}
