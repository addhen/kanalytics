// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer

import kotlin.jvm.JvmInline

public class RetentionPolicyManager(
  private val clock: Any = Any(),
  private val dayDuration: Any,
  private val repository: Any,
) {

  public suspend fun processDataRetention() {
    // No-op for no-op artifact
  }

  @JvmInline
  public value class DayDuration(public val numberOfDays: Int)
}
