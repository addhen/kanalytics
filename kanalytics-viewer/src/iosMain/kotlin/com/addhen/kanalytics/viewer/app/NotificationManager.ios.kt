// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app

import co.touchlab.kermit.Logger
import platform.Foundation.NSError
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNUserNotificationCenter

internal actual fun NotificationManager(): NotificationManager {
  return NotificationManagerImpl()
}

internal class NotificationManagerImpl : NotificationManager {
  private val notificationId: String = "com.gyanoba.inspektor.notification"

  override fun showNotification(eventName: String, trackerName: String) {
    // Implement notification using notification center
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
      }

      // Create a new notification
      val request = UNNotificationRequest.requestWithIdentifier(
        notificationId,
        content,
        null,
      )

      notificationCenter.addNotificationRequest(request) { error ->
        if (error != null) {
          Logger.e(error.toThrowable()) {
            "Error adding notification request: ${error.localizedDescription}"
          }
        }
      }
    } catch (e: IllegalStateException) {
      Logger.e(e) { "Error requesting notification permission: $e" }
    }
  }

  private fun NSError.toThrowable(): Throwable {
    return Throwable(this.localizedDescription)
  }
}
