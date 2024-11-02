// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import kotlinx.datetime.DateTimeUnit

public class RetentionPolicyManager(
  period: Period = Period.ONE_DAY,
) {

  private val dateTimeUnit: DateTimeUnit = period.toMillis()

  public fun manage(): Unit = Unit

  private fun Period.toMillis(): DateTimeUnit {
    return when (this) {
      Period.ONE_HOUR -> DateTimeUnit.HOUR
      Period.ONE_DAY -> DateTimeUnit.DAY
      Period.ONE_WEEK -> DateTimeUnit.WEEK
      Period.FOREVER -> DateTimeUnit.CENTURY
    }
  }

  public enum class Period {
    ONE_HOUR,
    ONE_DAY,
    ONE_WEEK,
    FOREVER,
  }
}
