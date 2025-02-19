// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app

import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationAction
import platform.UserNotifications.UNNotificationActionOptionForeground
import platform.UserNotifications.UNNotificationCategory
import platform.UserNotifications.UNNotificationCategoryOptionNone
import platform.UserNotifications.UNNotificationInterruptionLevel
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter

internal const val NOTIFICATION_ACTION_ID: String = "com.addhen.kanalytics.notificationAction"

internal actual fun NotificationManager(): NotificationManager = NotificationManagerIOsImpl()

internal class NotificationManagerIOsImpl : NotificationManager {
  private val notificationId: String = "com.addhen.kanalytics.notification"
  private val notificationCategoryId: String = "com.addhen.kanalytics.notificationCategory"

  private val notificationCenter = UNUserNotificationCenter.currentNotificationCenter().apply {
    setDelegate(NotificationDelegate())
  }

  private val notificationAction = UNNotificationAction.actionWithIdentifier(
    identifier = NOTIFICATION_ACTION_ID,
    title = "Open Viewer App",
    options = UNNotificationActionOptionForeground,
  )

  private val notificationCategory = UNNotificationCategory.categoryWithIdentifier(
    identifier = notificationCategoryId,
    actions = listOf(notificationAction),
    intentIdentifiers = listOf<String>(),
    options = UNNotificationCategoryOptionNone,
  )

  override fun showNotification(eventName: String, trackerName: String) {
    if (!notificationCenter.isNotificationPermissionGranted()) {
      return
    }

    val body = "$eventName sent to $trackerName"
    val content = UNMutableNotificationContent().apply {
      setTitle("Analytics Event Sent!")
      setBody(body)
      setCategoryIdentifier(notificationCategoryId)
      setSound(UNNotificationSound.defaultSound)
      setInterruptionLevel(UNNotificationInterruptionLevel.UNNotificationInterruptionLevelActive)
    }

    val trigger = UNTimeIntervalNotificationTrigger
      .triggerWithTimeInterval(0.1, repeats = false)
    val request = UNNotificationRequest
      .requestWithIdentifier(notificationId, content, trigger)

    notificationCenter.setNotificationCategories(setOf(notificationCategory))
    notificationCenter.addNotificationRequest(request) { error ->
      if (error != null) {
        println("Error adding notification request: ${error.localizedDescription}")
      } else {
        println("Notification request added successfully.")
      }
    }
  }

  override fun clearBuffer() {
    // Clear the notification buffer
    notificationCenter.removeDeliveredNotificationsWithIdentifiers(listOf(notificationId))
    notificationCenter.removePendingNotificationRequestsWithIdentifiers(listOf(notificationId))
  }

  private fun UNUserNotificationCenter.isNotificationPermissionGranted(): Boolean = try {
    // Requesting notification permissions
    requestAuthorizationWithOptions(
      UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge,
    ) { granted, error ->
      if (!granted || error != null) {
        throw IllegalStateException(
          error?.localizedDescription ?: "Error requesting notification permissions.",
        )
      }
    }
    true
  } catch (e: IllegalStateException) {
    println("Error requesting notification permission: ${e.message}")
    false
  }
}
