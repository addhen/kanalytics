// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal fun Instant.toFormattedString(
  timezone: TimeZone = TimeZone.currentSystemDefault(),
): String {
  val localDateTime = this.toLocalDateTime(timezone)
  return buildString {
    append(localDateTime.year.toString().padStart(4, '0'))
    append('-')
    append(localDateTime.monthNumber.toString().padStart(2, '0'))
    append('-')
    append(localDateTime.dayOfMonth.toString().padStart(2, '0'))
    append(' ')
    append(localDateTime.hour.toString().padStart(2, '0'))
    append(':')
    append(localDateTime.minute.toString().padStart(2, '0'))
    append(':')
    append(localDateTime.second.toString().padStart(2, '0'))
    append('.')
    append(localDateTime.nanosecond.toString().take(3).padStart(3, '0'))
  }
}
