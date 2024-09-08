// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.kanalytics

public interface Interceptor {

  public fun intercept(event: KAnalyticsEvent)
}
