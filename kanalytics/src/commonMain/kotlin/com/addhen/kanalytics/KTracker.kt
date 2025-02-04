// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import kotlin.jvm.JvmInline

@JvmInline
public value class KTrackerName(public val value: String)

public interface KTracker {

  public fun send(event: KAnalyticsEvent)
}
