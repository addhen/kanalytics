// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.shared.trackers

import co.touchlab.kermit.Logger
import com.addhen.kanalytics.KAnalyticsEvent
import com.addhen.kanalytics.KTracker

public class AirshipTracker : KTracker {

  private val analyticsEvents = mutableListOf<KAnalyticsEvent>()

  override fun send(event: KAnalyticsEvent) {
    // Send event to firebase
    analyticsEvents.add(event)
    Logger.d { "AirshipTracker: $event" }
  }
}
