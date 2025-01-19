// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app

import co.touchlab.kermit.Logger
import com.addhen.kanalytics.viewer.app.shared.ui.launchViewerApp
import platform.Foundation.NSError
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationAction
import platform.UserNotifications.UNNotificationActionOptionForeground
import platform.UserNotifications.UNNotificationCategory
import platform.UserNotifications.UNNotificationCategoryOptionNone
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationResponse
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNUserNotificationCenter
import platform.UserNotifications.UNUserNotificationCenterDelegateProtocol
import platform.darwin.NSObject

internal actual fun NotificationManager(): NotificationManager {
  return NotificationManagerImpl()
}

internal class NotificationManagerImpl : NotificationManager {
  private val notificationId: String = "com.addhen.kanalytics.notification"

  private val notificationAction = UNNotificationAction.actionWithIdentifier(
    identifier = "OPEN_VIEWER_APP_ACTION",
    title = "Open Viewer App",
    options = UNNotificationActionOptionForeground,
  )

  private val notificationCategory = UNNotificationCategory.categoryWithIdentifier(
    identifier = "KANALYTICS_CATEGORY",
    actions = listOf(notificationAction),
    intentIdentifiers = listOf<String>(),
    options = UNNotificationCategoryOptionNone,
  )

  override fun showNotification(eventName: String, trackerName: String) {
    val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
    try {
      // Requesting notification permissions
      notificationCenter.requestAuthorizationWithOptions(
        options = UNAuthorizationOptionAlert +
          UNAuthorizationOptionBadge +
          UNAuthorizationOptionSound,
      ) { granted, error ->
        if (!granted || error != null) {
          throw IllegalStateException(
            error?.localizedDescription ?: "Error requesting notification permissions.",
          )
        }
      }

      val content = UNMutableNotificationContent().apply {
        setTitle(eventName)
        setBody(trackerName)
        setCategoryIdentifier("KANALYTICS_CATEGORY")
        setSound(UNNotificationSound.defaultSound())
      }

      // Create a new notification
      val request = UNNotificationRequest.requestWithIdentifier(
        notificationId,
        content,
        null,
      )

      notificationCenter.setNotificationCategories(setOf(notificationCategory))
      notificationCenter.addNotificationRequest(request) { error ->
        if (error != null) {
          Logger.e(error.toThrowable()) {
            "Error adding notification request: ${error.localizedDescription}"
          }
        } else {
          Logger.i { "Notification request added successfully." }
        }
      }

      notificationCenter.delegate = object : NSObject(), UNUserNotificationCenterDelegateProtocol {
        override fun userNotificationCenter(
          center: UNUserNotificationCenter,
          didReceiveNotificationResponse: UNNotificationResponse,
          withCompletionHandler: () -> Unit,
        ) {
          if (didReceiveNotificationResponse.actionIdentifier == "OPEN_VIEWER_APP_ACTION") {
            launchViewerApp()
          }
          withCompletionHandler()
        }
      }
    } catch (e: IllegalStateException) {
      Logger.e(e) { "Error requesting notification permission: $e" }
    }
  }

  override fun clearBuffer() {
    // Clear the notification buffer
    val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()
    notificationCenter.removeDeliveredNotificationsWithIdentifiers(listOf(notificationId))
  }

  private fun NSError.toThrowable(): Throwable {
    return Throwable(this.localizedDescription)
  }
}
