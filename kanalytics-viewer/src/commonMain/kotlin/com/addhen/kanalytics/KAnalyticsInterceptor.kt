// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import co.touchlab.kermit.Logger
import kotlinx.datetime.Clock

@Suppress("UnusedPrivateMember", "UNUSED_PARAMETER")
public class KAnalyticsInterceptor private constructor(builder: Builder) : Interceptor {

  private val collector = builder.collector ?: KAnalyticsCollector.Instance

  public constructor() : this(Builder())

  public override fun intercept(event: KAnalyticsEvent, tracker: Tracker): KAnalyticsEvent {
    Logger.d("Intercepting eventsss: $event")
    collector.onEventSent(event, tracker::class.simpleName!!, Clock.System.now())
    return event
  }

  public fun redactHeaders(vararg keyNames: String): KAnalyticsInterceptor {
    return this
  }

  public class Builder {

    internal var collector: KAnalyticsCollector? = null

    public fun collector(collector: KAnalyticsCollector): Builder = apply {
      this.collector = collector
    }

    internal fun build(): KAnalyticsInterceptor = KAnalyticsInterceptor(this)

    internal fun redactParams(keyNames: Iterable<String>): Builder = this

    internal fun redactParams(vararg keyNames: String): Builder = this
  }
}
