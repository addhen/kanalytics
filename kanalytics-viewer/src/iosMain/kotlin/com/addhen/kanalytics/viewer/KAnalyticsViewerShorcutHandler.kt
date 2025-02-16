// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer

import kotlinx.cinterop.BetaInteropApi
import platform.UIKit.UIApplicationShortcutItem
import platform.UIKit.UIScene
import platform.UIKit.UISceneConfiguration
import platform.UIKit.UISceneConnectionOptions
import platform.UIKit.UISceneSession
import platform.UIKit.UIWindowScene
import platform.UIKit.UIWindowSceneDelegateProtocol
import platform.darwin.NSObject

// Credits: https://github.com/BVantur/inspektify/blob/main/inspektify/src/iosMain/kotlin/sp/bvantur/inspektify/ktor/client/InspektifyShortcutHandler.kt
internal class KAnalyticsViewerSceneDelegate @OverrideInit constructor() :
  NSObject(),
  UIWindowSceneDelegateProtocol {

  override fun scene(
    scene: UIScene,
    willConnectToSession: UISceneSession,
    options: UISceneConnectionOptions,
  ) {
    onShortcutAction(options.shortcutItem)
  }

  override fun windowScene(
    windowScene: UIWindowScene,
    performActionForShortcutItem: UIApplicationShortcutItem,
    completionHandler: (Boolean) -> Unit,
  ) {
    onShortcutAction(performActionForShortcutItem)
    launchViewerApp()
    completionHandler(true)
  }

  private fun onShortcutAction(shortcutItem: UIApplicationShortcutItem?) {
    shortcutItem ?: return
    if (shortcutItem.type != getShortcutType()) return

    launchViewerApp()
  }
}

@OptIn(BetaInteropApi::class)
public fun getUISceneConfiguration(
  configurationForConnectingSceneSession: UISceneSession,
): UISceneConfiguration {
  val configuration = UISceneConfiguration(
    name = configurationForConnectingSceneSession.configuration.name,
    sessionRole = configurationForConnectingSceneSession.role,
  )
  configuration.delegateClass = KAnalyticsViewerSceneDelegate().`class`()
  return configuration
}

@Suppress("FunctionOnlyReturningConstant")
public fun getShortcutType(): String = SHORTCUT_TYPE
