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
import com.addhen.kanalytics.viewer.R
import com.addhen.kanalytics.viewer.app.android.ContextInitializer
import com.addhen.kanalytics.viewer.app.android.MainActivity

private const val CHANNEL_ID = "com.addhen.kanalytics.viewer.app.android.channel"
private const val BUFFER_SIZE = 10

internal actual fun NotificationManager(): NotificationManager {
  return NotificationManagerImpl.Instance
}

internal class NotificationManagerImpl : NotificationManager {
  private val notificationId = 10000
  private val notificationManager = ContextInitializer
    .applicationContext
    .getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

  private val notificationBuffer = mutableListOf<String>()

  override fun clearBuffer() {
    synchronized(notificationBuffer) {
      notificationBuffer.clear()
    }
  }

  override fun showNotification(eventName: String, trackerName: String) {
    val context = ContextInitializer.applicationContext
    addNotificationToBuffer(
      context.getString(R.string.notification_message, eventName, trackerName),
    )
    if (MainActivity.isInForeground) {
      return
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      createNotificationChannel(context)
    }
    notificationManager.notify(notificationId, createNotification(context))
  }

  private fun createNotification(context: Context): Notification {
    val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
      .setContentTitle(context.getString(R.string.notification_title))
      .setSmallIcon((R.drawable.ic_app_icon))
      .setAutoCancel(true)
      .setChannelId(CHANNEL_ID)
      .setContentIntent(
        PendingIntent.getActivity(
          context,
          0,
          Intent(context, MainActivity::class.java),
          PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        ),
      )
    val inboxStyle = NotificationCompat.InboxStyle()
    synchronized(notificationBuffer) {
      for ((counter, i) in (notificationBuffer.lastIndex downTo 0).withIndex()) {
        val notification = notificationBuffer[i]
        if (counter < BUFFER_SIZE) {
          if (counter == 0) {
            notificationBuilder.setContentTitle(context.getString(R.string.notification_title))
          }
          inboxStyle.addLine(notification)
        }
      }
      notificationBuilder.setStyle(inboxStyle)
      notificationBuilder.setSubText(notificationBuffer.size.toString())
    }

    return notificationBuilder.build()
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun createNotificationChannel(context: Context) {
    val channel = android.app.NotificationChannel(
      CHANNEL_ID,
      context.getString(R.string.viewer_app_name),
      android.app.NotificationManager.IMPORTANCE_DEFAULT,
    )
    notificationManager.createNotificationChannel(channel)
  }

  private fun addNotificationToBuffer(notification: String) {
    synchronized(notificationBuffer) {
      notificationBuffer.add(notification)
    }
  }

  internal companion object {
    val Instance: NotificationManager by lazy { NotificationManagerImpl() }
  }
}
