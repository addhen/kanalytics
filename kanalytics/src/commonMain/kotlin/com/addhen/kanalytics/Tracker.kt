// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import com.addhen.kanalytics.interceptor.KAnalyticsEvent

public interface Tracker {

  public fun send(event: KAnalyticsEvent)
}
