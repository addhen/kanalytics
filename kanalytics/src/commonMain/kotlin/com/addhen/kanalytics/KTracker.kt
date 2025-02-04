// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import kotlin.jvm.JvmInline

@JvmInline
public value class KTrackerName(public val value: String)

/**
 * [KTracker] is an interface that defines a contract for sending events to a specific analytics
 * platform.
 *
 * Implementations of this interface should provide the logic for sending events to the desired
 * platform.
 */
public interface KTracker {

  public fun send(event: KAnalyticsEvent)
}
