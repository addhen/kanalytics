// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app

internal expect fun NotificationManager(): NotificationManager

public interface NotificationManager {
  public fun showNotification(eventName: String, trackerName: String)
  public fun clearBuffer()
}
