// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import kotlinx.datetime.Clock

@Suppress("UnusedPrivateMember", "UNUSED_PARAMETER")
public class KAnalyticsInterceptor private constructor(builder: Builder) : Interceptor {

  private val collector = builder.collector ?: KAnalyticsCollector.instance

  public constructor() : this(Builder())

  public fun redactHeaders(vararg keyNames: String): KAnalyticsInterceptor {
    return this
  }

  internal class Builder {

    internal var collector: KAnalyticsCollector? = null

    fun collector(collector: KAnalyticsCollector): Builder = apply {
      this.collector = collector
    }

    fun build(): KAnalyticsInterceptor = KAnalyticsInterceptor(this)
  }

  override fun intercept(chain: Interceptor.Chain): KAnalyticsEvent {
    val event = chain.event
    collector.onEventSent(event, chain.trackerName, Clock.System.now())
    return event
  }
}
