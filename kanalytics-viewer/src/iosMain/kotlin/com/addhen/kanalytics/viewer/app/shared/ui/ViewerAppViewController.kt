package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

internal var viewerAppViewControllerInstance: UIViewController? = null

internal fun ViewerAppViewController(): UIViewController = ComposeUIViewController(configure = {
  enforceStrictPlistSanityCheck = false
}) {
  ViewerApp()
}.also {
  viewerAppViewControllerInstance = it
}
