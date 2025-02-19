// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app

import com.addhen.kanalytics.viewer.launchViewerApp
import platform.UserNotifications.UNNotification
import platform.UserNotifications.UNNotificationPresentationOptionAlert
import platform.UserNotifications.UNNotificationPresentationOptionBadge
import platform.UserNotifications.UNNotificationPresentationOptionSound
import platform.UserNotifications.UNNotificationPresentationOptions
import platform.UserNotifications.UNNotificationResponse
import platform.UserNotifications.UNUserNotificationCenter
import platform.UserNotifications.UNUserNotificationCenterDelegateProtocol
import platform.darwin.NSObject

internal class NotificationDelegate :
  NSObject(),
  UNUserNotificationCenterDelegateProtocol {

  override fun userNotificationCenter(
    center: UNUserNotificationCenter,
    willPresentNotification: UNNotification,
    withCompletionHandler: (UNNotificationPresentationOptions) -> Unit,
  ) {
    withCompletionHandler(
      UNNotificationPresentationOptionAlert or
        UNNotificationPresentationOptionSound or
        UNNotificationPresentationOptionBadge,
    )
  }

  override fun userNotificationCenter(
    center: UNUserNotificationCenter,
    didReceiveNotificationResponse: UNNotificationResponse,
    withCompletionHandler: () -> Unit,
  ) {
    launchViewerApp()
    // Call the completion handler when you're done.
    withCompletionHandler()
  }
}
