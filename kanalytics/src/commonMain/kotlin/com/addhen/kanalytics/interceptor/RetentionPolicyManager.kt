// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.interceptor

public class RetentionPolicyManager(
  private val period: Period,
) {

  public fun manage(): Unit = Unit

  public enum class Period {
    ONE_HOUR,
    ONE_DAY,
    ONE_WEEK,
    FOREVER,
  }
}
