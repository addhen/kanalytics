// Copyright 2025, Addhen Ltd and the kanalytics project contributors
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
import com.addhen.kanalytics.viewer.app.android.R

private const val CHANNEL_ID = "com.addhen.kanalytics.viewer.app.android.channel"

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
      createNotificationChannel(context)
    }
    notificationManager.notify(
      notificationId,
      createNotification(context, title, message),
    )
  }

  private fun createNotification(
    context: Context,
    eventName: String,
    trackerName: String,
  ): Notification {
    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
      .setContentTitle(context.getString(R.string.notification_title))
      .setContentText(context.getString(R.string.notification_message, eventName, trackerName))
      .setSmallIcon((R.drawable.ic_app_icon))
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
  private fun createNotificationChannel(context: Context) {
    val channel = android.app.NotificationChannel(
      CHANNEL_ID,
      context.getString(R.string.app_name),
      android.app.NotificationManager.IMPORTANCE_DEFAULT,
    )
    notificationManager.createNotificationChannel(channel)
  }
}
