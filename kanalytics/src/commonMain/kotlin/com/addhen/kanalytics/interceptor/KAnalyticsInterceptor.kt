// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.interceptor

import com.addhen.kanalytics.Interceptor
import com.addhen.kanalytics.KAnalyticsEvent

public class KAnalyticsInterceptor : Interceptor {

  public override fun intercept(event: KAnalyticsEvent): Unit = Unit
}
