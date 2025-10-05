// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer

import com.addhen.kanalytics.Interceptor
import com.addhen.kanalytics.KAnalyticsEvent
import kotlin.time.Clock

public class KAnalyticsInterceptor(
  private val collector: KAnalyticsCollector = KAnalyticsCollector(),
  private val clock: Clock = Clock.System,
) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): KAnalyticsEvent {
    val event = chain.event
    collector.onEventSent(event, chain.kTrackerName, clock.now())
    return event
  }
}
