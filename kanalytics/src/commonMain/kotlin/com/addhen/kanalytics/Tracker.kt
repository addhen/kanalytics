// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.kanalytics

public interface Tracker {

  public fun send(event: KAnalyticsEvent)
}
