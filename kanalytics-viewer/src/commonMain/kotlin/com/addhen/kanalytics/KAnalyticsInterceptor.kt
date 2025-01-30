// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import kotlinx.datetime.Clock

public class KAnalyticsInterceptor(numberOfDays: Int, showNotification: Boolean) : Interceptor {

  private val collector: KAnalyticsCollector = KAnalyticsCollector.getInstance(
    showNotification,
    RetentionPolicyManager.DayDuration(numberOfDays)
  )

  override fun intercept(chain: Interceptor.Chain): KAnalyticsEvent {
    val event = chain.event
    collector.onEventSent(event, chain.trackerName, Clock.System.now())
    return event
  }
}
