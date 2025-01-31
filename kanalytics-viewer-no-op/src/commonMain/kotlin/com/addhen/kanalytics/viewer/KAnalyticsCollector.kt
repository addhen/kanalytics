// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer

import com.addhen.kanalytics.KAnalyticsEvent
import com.addhen.kanalytics.TrackerName
import kotlinx.datetime.Instant

public class KAnalyticsCollector(
  private val repository: Any,
  private val showNotification: Boolean = true,
  duration: RetentionPolicyManager.DayDuration = RetentionPolicyManager.DayDuration(7),
  private val notificationManager: Any,
  private val appCoroutineDispatchers: Any
) {

  public fun onEventSent(
    kAnalyticsEvent: KAnalyticsEvent,
    trackerName: TrackerName,
    onSentDate: Instant
  ) {
    // No-op for no-op artifact
  }
}
