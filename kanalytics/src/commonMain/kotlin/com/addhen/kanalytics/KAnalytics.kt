// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import com.addhen.kanalytics.KAnalytics.Builder
import com.addhen.kanalytics.internal.DefaultInterceptorChain
import kotlin.reflect.KClass
import kotlinx.atomicfu.atomic
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet

/**
 * KAnalytics is a class that provides a centralized way to manage and dispatch analytics
 * events to multiple trackers.
 *
 * It allows for flexible configuration of trackers and interceptors, enabling event modification
 * and filtering before being sent.
 *
 * @param builder The builder for the KAnalytics instance. Use the [Builder] to configure trackers
 * and interceptors.
 */
public class KAnalytics internal constructor(builder: Builder) {

  private val kTrackers: ImmutableList<KTracker> = builder.kTrackers.toImmutableList()
  private val interceptors: ImmutableList<Interceptor> = builder.interceptors.toImmutableList()

  public constructor() : this(Builder())

  /**
   * Sends an event to all trackers
   *
   * @param event The event to send
   */
  public fun send(event: KAnalyticsEvent) {
    send(kTrackers, event)
  }

  /**
   * Sends an event to the specified trackers
   *
   * @param event The event to send
   * @param kTrackerNames The trackers to send the event to
   */
  public fun send(event: KAnalyticsEvent, vararg kTrackerNames: KClass<out KTracker>) {
    val trackerNamesSet = kTrackerNames.toImmutableSet()
    val filteredTrackers = kTrackers.filter { trackerNamesSet.contains(it::class) }

    send(filteredTrackers, event)
  }

  /**
   * Sends an event to all trackers
   *
   * @param eventName The name of the event to send
   * @param fieldName The name of the field to add to the event
   * @param fieldValue The value of the field to add to the event
   */
  public fun send(eventName: String, fieldName: String, fieldValue: Any?) {
    val event = KAnalyticsEvent(eventName = eventName).apply {
      addParameter(fieldName, fieldValue)
    }
    send(event)
  }

  /**
   * Gets a new builder instance for KAnalytics
   */
  public fun newBuilder(): Builder = Builder(this)

  private fun send(kTrackers: List<KTracker>, event: KAnalyticsEvent) {
    if (kTrackers.isEmpty()) return

    if (!interceptors.isEmpty()) {
      kTrackers.forEach { tracker ->
        val chain = DefaultInterceptorChain(
          interceptors = interceptors,
          index = 0,
          kTrackerName = KTrackerName(this::class.simpleName ?: ""),
          event = event,
        )
        val interceptedEvent = chain.proceed(event)
        tracker.send(interceptedEvent.copy())
      }
    } else {
      kTrackers.forEach { it.send(event.copy()) }
    }
  }

  /**
   * Builder class for KAnalytics
   */
  public class Builder() {
    private val trackersAtomicRef = atomic(mutableListOf<KTracker>())
    private val interceptorsAtomicRef = atomic(mutableListOf<Interceptor>())

    internal val kTrackers: List<KTracker>
      get() = trackersAtomicRef.value
    internal val interceptors: List<Interceptor>
      get() = interceptorsAtomicRef.value

    internal constructor(kAnalytics: KAnalytics) : this() {
      this.trackersAtomicRef.value += kAnalytics.kTrackers
      this.interceptorsAtomicRef.value += kAnalytics.interceptors
    }

    /**
     * Add a tracker to the list of trackers
     */
    public fun addTracker(kTracker: KTracker): Builder = apply {
      trackersAtomicRef.value.add(kTracker)
    }

    /**
     * Add an interceptor to the list of interceptors
     */
    public fun addInterceptor(interceptor: Interceptor): Builder = apply {
      interceptorsAtomicRef.value.add(interceptor)
    }

    /**
     * Build the KAnalytics instance
     */
    public fun build(): KAnalytics = KAnalytics(this)
  }
}
