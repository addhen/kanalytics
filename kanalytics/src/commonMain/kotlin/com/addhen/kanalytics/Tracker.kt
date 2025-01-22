// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import kotlin.jvm.JvmInline

@JvmInline
public value class TrackerName(public val value: String)

public interface Tracker {

  public fun send(event: KAnalyticsEvent)
}
