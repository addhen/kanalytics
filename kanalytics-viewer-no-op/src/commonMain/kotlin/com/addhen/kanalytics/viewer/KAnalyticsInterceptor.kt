// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer

import com.addhen.kanalytics.Interceptor
import com.addhen.kanalytics.KAnalyticsEvent

public class KAnalyticsInterceptor(numberOfDays: Int, showNotification: Boolean) : Interceptor {

  override fun intercept(chain: Interceptor.Chain): KAnalyticsEvent {
    val event = chain.event
    return chain.proceed(event)
  }
}
