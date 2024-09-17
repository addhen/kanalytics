// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import com.addhen.kanalytics.interceptor.Interceptor
import com.addhen.kanalytics.interceptor.KAnalyticsEvent
import kotlin.reflect.KClass
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * KAnalytics is a multiplatform analytics library that provides a simple way to track events.
 * val kanalytics = KAnalytics.Builder()
 * .addTracker(FirebaseTracker())
 * .addTracker(AdjustTracker())
 * .addInterceptor(AnalyticsInterceptor())
 * .build()
 */
public class KAnalytics internal constructor(builder: Builder) {

  public val trackers: ImmutableList<Tracker> = builder.trackers.toImmutableList()
  public val interceptors: ImmutableList<Interceptor> = builder.interceptors.toImmutableList()

  public constructor() : this(Builder())

  public fun send(event: KAnalyticsEvent) {
    send(trackers, event)
  }

  public fun send(event: KAnalyticsEvent, vararg trackerNames: KClass<out Tracker>) {
    send(
      trackers.filter { trackerNames.indexOf(it::class) >= 0 },
      event,
    )
  }

  public fun send(eventName: String, fieldName: String, fieldValue: Any?) {
    val event = KAnalyticsEvent(eventName = eventName).apply {
      addParameter(fieldName, fieldValue)
    }
    send(event)
  }

  public fun newBuilder(): Builder = Builder(this)

  private fun send(trackers: List<Tracker>, event: KAnalyticsEvent) {
    if (trackers.isEmpty()) return

    interceptors.forEach { it.intercept(event) }
    trackers.forEach { it.send(event) }
  }

  /**
   * Builder class for KAnalytics
   */
  public class Builder() {

    internal val trackers: MutableList<Tracker> = mutableListOf()
    internal val interceptors: MutableList<Interceptor> = mutableListOf()

    internal constructor(kAnalytics: KAnalytics) : this() {
      this.trackers += kAnalytics.trackers
      this.interceptors += kAnalytics.interceptors
    }

    /**
     * Add a tracker to the list of trackers
     */
    public fun addTracker(tracker: Tracker): Builder = apply {
      trackers.add(tracker)
    }

    /**
     * Add an interceptor to the list of interceptors
     */
    public fun addInterceptor(interceptor: Interceptor): Builder = apply {
      interceptors.add(interceptor)
    }

    /**
     * Build the KAnalytics instance
     */
    public fun build(): KAnalytics = KAnalytics(this)
  }
}
