// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import kotlinx.datetime.Clock

@Suppress("UnusedPrivateMember", "UNUSED_PARAMETER")
public class KAnalyticsInterceptor private constructor(builder: Builder) : Interceptor {

  private val collector = builder.collector ?: KAnalyticsCollector.Instance

  public constructor() : this(Builder())

  public override fun intercept(event: KAnalyticsEvent, tracker: Tracker): KAnalyticsEvent {
    collector.onEventSent(event, tracker::class.simpleName!!, Clock.System.now())
    return event
  }

  public fun redactHeaders(vararg keyNames: String): KAnalyticsInterceptor {
    return this
  }

  internal class Builder {

    internal var collector: KAnalyticsCollector? = null

    public fun collector(collector: KAnalyticsCollector): Builder = apply {
      this.collector = collector
    }

    fun build(): KAnalyticsInterceptor = KAnalyticsInterceptor(this)

    fun redactParams(keyNames: Iterable<String>): Builder = this

    fun redactParams(vararg keyNames: String): Builder = this
  }
}
