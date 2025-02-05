// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.internal

import com.addhen.kanalytics.Interceptor
import com.addhen.kanalytics.KAnalyticsEvent
import com.addhen.kanalytics.KTrackerName
import kotlinx.atomicfu.atomic

internal class DefaultInterceptorChain(
  private val interceptors: List<Interceptor>,
  private val index: Int,
  override val kTrackerName: KTrackerName,
  override val event: KAnalyticsEvent,
) : Interceptor.Chain {
  private val calls = atomic(0)

  internal fun copy(
    index: Int = this.index,
    kTrackerName: KTrackerName = this.kTrackerName,
    event: KAnalyticsEvent = this.event,
  ) = DefaultInterceptorChain(interceptors, index, kTrackerName, event)

  override fun proceed(event: KAnalyticsEvent): KAnalyticsEvent {
    check(calls.incrementAndGet() == 1) {
      "interceptor ${interceptors[index]} must call proceed() exactly once"
    }
    if (index >= interceptors.size) {
      return event
    }

    val nextChain = copy(index = index + 1, event = event)
    return interceptors[index].intercept(nextChain)
  }

  override fun toString(): String =
    "DefaultInterceptorChain(index=$index, trackerName=$kTrackerName, event=$event)"
}
