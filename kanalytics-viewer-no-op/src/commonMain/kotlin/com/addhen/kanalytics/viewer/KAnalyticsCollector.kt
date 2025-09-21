// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer

import com.addhen.kanalytics.KAnalyticsEvent
import com.addhen.kanalytics.KTrackerName
import kotlin.time.Instant

public class KAnalyticsCollector(
  private val showNotification: Boolean = false,
  duration: RetentionPolicyManager.DayDuration = RetentionPolicyManager.DayDuration(7),
) {

  public fun onEventSent(
    kAnalyticsEvent: KAnalyticsEvent,
    KTrackerName: KTrackerName,
    onSentDate: Instant,
  ) {
    // No-op for no-op artifact
  }
}
