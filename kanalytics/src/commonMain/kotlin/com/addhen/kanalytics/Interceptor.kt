// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

//
public fun interface Interceptor {
  public fun intercept(chain: Chain): KAnalyticsEvent

  public companion object {
    public inline operator fun invoke(
      crossinline block: (chain: Chain) -> KAnalyticsEvent,
    ): Interceptor = Interceptor { block(it) }
  }

  public interface Chain {

    public val event: KAnalyticsEvent
    public val trackerName: TrackerName

    public fun proceed(event: KAnalyticsEvent): KAnalyticsEvent
  }
}
