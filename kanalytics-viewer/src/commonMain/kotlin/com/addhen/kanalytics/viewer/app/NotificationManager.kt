// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app

internal expect fun NotificationManager(): NotificationManager

internal interface NotificationManager {
  fun showNotification(title: String, message: String)
}
