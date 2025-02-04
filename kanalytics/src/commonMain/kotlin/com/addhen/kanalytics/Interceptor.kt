// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

/**
 * An interceptor for [KAnalyticsEvent]s that can be used to modify the event before it is
 * sent to trackers.
 */
public fun interface Interceptor {

  /**
   * Intercepts the [KAnalyticsEvent] and returns a modified event.
   *
   * @param chain The chain of interceptors and trackers.
   * @return The modified [KAnalyticsEvent].
   */
  public fun intercept(chain: Chain): KAnalyticsEvent

  public companion object {
    public inline operator fun invoke(
      crossinline block: (chain: Chain) -> KAnalyticsEvent,
    ): Interceptor = Interceptor { block(it) }
  }

  /**
   * The chain of interceptors and trackers.
   */
  public interface Chain {

    public val event: KAnalyticsEvent
    public val kTrackerName: KTrackerName

    /**
     * Proceeds to the next interceptor in the chain.
     *
     * @param event The event to send to the next interceptor or tracker.
     * @return The result of the next interceptor or tracker.
     */
    public fun proceed(event: KAnalyticsEvent): KAnalyticsEvent
  }
}
