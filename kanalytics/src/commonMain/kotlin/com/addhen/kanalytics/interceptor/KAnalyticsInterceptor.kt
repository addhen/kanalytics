// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.interceptor

import com.addhen.kanalytics.Interceptor
import com.addhen.kanalytics.KAnalyticsEvent

@Suppress("UnusedPrivateMember", "UNUSED_PARAMETER")
public class KAnalyticsInterceptor private constructor(builder: Builder): Interceptor {

  public constructor() : this(Builder())

  public override fun intercept(event: KAnalyticsEvent): Unit = Unit

  public fun redactHeaders(vararg keyNames: String): KAnalyticsInterceptor = this

  public class Builder {

    public fun build(): KAnalyticsInterceptor = KAnalyticsInterceptor(this)

    public fun redactParams(keyNames: Iterable<String>): Builder = this

    public fun redactParams(vararg keyNames: String): Builder = this
  }
}
