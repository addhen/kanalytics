// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import com.addhen.kanalytics.KAnalytics.Builder
import com.addhen.kanalytics.internal.DefaultInterceptorChain
import kotlin.reflect.KClass
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
  private val sendingEnabled: Boolean = builder.sendingEnabled

  public constructor() : this(Builder())

  /**
   * Sends an event to all trackers
   *
   * @param event The event to send
   * @throws [IllegalStateException] if the event name is blank
   */
  public fun send(event: KAnalyticsEvent) {
    check(event.eventName.isNotBlank()) { "Event name cannot be blank" }
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
      addProperty(fieldName, fieldValue)
    }
    send(event)
  }

  /**
   * Gets a new builder instance for KAnalytics
   */
  public fun newBuilder(): Builder = Builder(this)

  /**
   * Sets whether event sending is enabled
   *
   * @param enabled True to enable event sending, false to disable
   * @return This KAnalytics instance with the new sending enabled state
   */
  public fun enabled(enabled: Boolean): KAnalytics =
    newBuilder().enabled(enabled).build()

  private fun send(kTrackers: List<KTracker>, event: KAnalyticsEvent) {
    if (!sendingEnabled) return

    if (kTrackers.isEmpty()) return

    if (!interceptors.isEmpty()) {
      kTrackers.forEach { tracker ->
        val chain = DefaultInterceptorChain(
          interceptors = interceptors,
          index = 0,
          kTrackerName = KTrackerName(tracker::class.simpleName ?: ""),
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
    internal val kTrackers = mutableListOf<KTracker>()
    internal val interceptors = mutableListOf<Interceptor>()
    internal var sendingEnabled = true

    internal constructor(kAnalytics: KAnalytics) : this() {
      this.kTrackers += kAnalytics.kTrackers
      this.interceptors += kAnalytics.interceptors
      this.sendingEnabled = kAnalytics.sendingEnabled
    }

    /**
     * Add a tracker to the list of trackers
     */
    public fun addTracker(kTracker: KTracker): Builder = apply {
      kTrackers.add(kTracker)
    }

    /**
     * Add an interceptor to the list of interceptors
     */
    public fun addInterceptor(interceptor: Interceptor): Builder = apply {
      interceptors.add(interceptor)
    }

    /**
     * Set whether event sending is enabled
     *
     * @param enabled True to enable event sending, false to disable
     */
    public fun enabled(enabled: Boolean): Builder = apply {
      sendingEnabled = enabled
    }

    /**
     * Build the KAnalytics instance
     *
     * @return [KAnalytics] instance
     * @throws [IllegalStateException] if no trackers are added
     */
    public fun build(): KAnalytics {
      check(kTrackers.isNotEmpty()) { "At least one tracker must be added." }
      return KAnalytics(this)
    }
  }
}
