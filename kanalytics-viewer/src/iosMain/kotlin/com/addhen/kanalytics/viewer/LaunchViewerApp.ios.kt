// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer

import com.addhen.kanalytics.viewer.app.shared.ui.viewerAppViewController
import com.addhen.kanalytics.viewer.app.shared.ui.viewerAppViewControllerInstance
import platform.Foundation.NSProcessInfo
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationShortcutIcon
import platform.UIKit.UIApplicationShortcutIconType
import platform.UIKit.UIApplicationShortcutItem
import platform.UIKit.UIModalPresentationFullScreen
import platform.UIKit.UINavigationController
import platform.UIKit.UITabBarController
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow
import platform.UIKit.UIWindowScene
import platform.UIKit.shortcutItems

public const val SHORTCUT_TYPE: String = "com.addhen.kanalytics.viewer.launch"

public actual fun launchViewerApp() {
  if (viewerAppViewControllerInstance != null) return // Already launched
  val viewerAppViewController = viewerAppViewController()
  viewerAppViewController.modalPresentationStyle = UIModalPresentationFullScreen
  getTopMostViewController()?.presentViewController(viewerAppViewController, true, null)
}

internal actual fun disposeViewerAppWindow() {
  viewerAppViewControllerInstance = null
}

private fun getTopMostViewController(
  base: UIViewController? = topWindow?.rootViewController,
): UIViewController? {
  if (base == null) return null

  return when (base) {
    is UINavigationController -> getTopMostViewController(base.visibleViewController)

    is UITabBarController -> {
      base.selectedViewController?.let { getTopMostViewController(it) }
    }

    else -> {
      if (base.presentedViewController != null) {
        getTopMostViewController(base.presentedViewController)
      } else {
        base
      }
    }
  }
}

private val topWindow: UIWindow?
  get() {
    return if (NSProcessInfo.processInfo.operatingSystemVersionString >= "15.0") {
      UIApplication.sharedApplication.connectedScenes
        .asSequence()
        .mapNotNull { it as? UIWindowScene }
        .mapNotNull { it.keyWindow }
        .lastOrNull()
    } else {
      UIApplication.sharedApplication.connectedScenes
        .asSequence()
        .flatMap { (it as? UIWindowScene)?.windows?.asSequence() ?: emptySequence() }
        .filterIsInstance<UIWindow>()
        .lastOrNull { it.isKeyWindow() }
    }
  }

public actual class DefaultShortcutManager : ShortcutManager {

  actual override fun setupShortcut(show: Boolean) {
    if (show) {
      setupShortcut()
    } else {
      UIApplication.sharedApplication.shortcutItems = UIApplication.sharedApplication
        .shortcutItems?.filter {
          if (it is UIApplicationShortcutItem) {
            it.type != SHORTCUT_TYPE
          } else {
            true
          }
        }
    }
  }
}

private fun setupShortcut() {
  if (UIApplication.sharedApplication.shortcutItems?.any {
      (it as? UIApplicationShortcutItem)?.type == SHORTCUT_TYPE
    } == true
  ) {
    return
  }
  val shortcutItem = UIApplicationShortcutItem(
    type = SHORTCUT_TYPE,

    localizedTitle = "KAnalytics Viewer",
    localizedSubtitle = "Open KAnalytics Viewer",
    icon = UIApplicationShortcutIcon.iconWithType(
      UIApplicationShortcutIconType.UIApplicationShortcutIconTypeCompose,
    ),
    userInfo = mapOf<Any?, Any>(),
  )
  UIApplication.sharedApplication.shortcutItems =
    (UIApplication.sharedApplication.shortcutItems?.toMutableList() ?: mutableListOf()).apply {
      add(shortcutItem)
    }
}
