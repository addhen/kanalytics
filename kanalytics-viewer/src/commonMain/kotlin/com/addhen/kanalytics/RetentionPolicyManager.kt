// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import com.addhen.kanalytics.viewer.app.shared.data.repository.EventRepository
import kotlin.jvm.JvmInline
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

public class RetentionPolicyManager(
  private val clock: Clock = Clock.System,
  private val dayDuration: DayDuration,
  private val repository: EventRepository,
) {

  public suspend fun processDataRetention() {
    val daysInMillis = dayDuration.numberOfDays * 24 * 60 * 60 * 1000L
    val currentTimeInMillis = clock.now().toEpochMilliseconds()
    val retentionDeadlineInMillis = currentTimeInMillis - daysInMillis
    repository.deleteAllOlderThan(Instant.fromEpochMilliseconds(retentionDeadlineInMillis))
  }

  @JvmInline
  public value class DayDuration(public val numberOfDays: Int)
}
