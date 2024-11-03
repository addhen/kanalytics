// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.addhen.kanalytics.viewer.app.android.ContextInitializer
import com.addhen.kanalytics.viewer.app.android.MainActivity

private const val CHANNEL_ID = "com.addhen.kanalytics.viewer.app"

internal actual fun NotificationManager(): NotificationManager {
  return NotificationManagerImpl()
}

internal class NotificationManagerImpl : NotificationManager {
  private val notificationId = 10000
  private val notificationManager = ContextInitializer
    .applicationContext
    .getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

  override fun showNotification(title: String, message: String) {
    val context = ContextInitializer.applicationContext

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      createNotificationChannel()
    }
    notificationManager.notify(
      notificationId,
      createNotification(context, title, message),
    )
  }

  private fun createNotification(context: Context, title: String, message: String): Notification {
    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
      .setContentTitle(title)
      .setContentText(message)
      .setOnlyAlertOnce(true)
      .setChannelId(CHANNEL_ID)
      .setContentIntent(
        PendingIntent.getActivity(
          context,
          0,
          Intent(context, MainActivity::class.java),
          PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        ),
      )
      .build()
    return notification
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun createNotificationChannel() {
    val channel = android.app.NotificationChannel(
      CHANNEL_ID,
      "KAnalytics Viewer",
      android.app.NotificationManager.IMPORTANCE_DEFAULT,
    )
    notificationManager.createNotificationChannel(channel)
  }
}
