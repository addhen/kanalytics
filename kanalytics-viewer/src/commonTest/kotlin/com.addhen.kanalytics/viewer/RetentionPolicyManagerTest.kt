// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer

import com.addhen.kanalytics.viewer.app.shared.data.repository.EventRepository
import dev.mokkery.MockMode
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class RetentionPolicyManagerTest {
  private lateinit var clock: TestClock
  private lateinit var repository: EventRepository
  private lateinit var manager: RetentionPolicyManager

  @BeforeTest
  fun setup() {
    clock = TestClock()
    repository = mock<EventRepository>(MockMode.autoUnit)
    manager = RetentionPolicyManager(
      clock = clock,
      dayDuration = RetentionPolicyManager.DayDuration(numberOfDays = 30),
      repository = repository,
    )
  }

  @Test
  fun `processDataRetention should calculate correct retention deadline`() = runTest {
    val currentTimeMillis = 1704200000000L // Some fixed timestamp
    clock.setCurrentTime(currentTimeMillis)

    val expectedRetentionDeadline = currentTimeMillis - (30L * 24 * 60 * 60 * 1000)

    manager.processDataRetention()

    verifySuspend {
      repository.deleteAllOlderThan(Instant.fromEpochMilliseconds(expectedRetentionDeadline))
    }
  }

  @Test
  fun `processDataRetention should work with different day durations`() = runTest {
    val currentTimeMillis = 1704200000000L
    clock.setCurrentTime(currentTimeMillis)

    val sevenDayManager = RetentionPolicyManager(
      clock = clock,
      dayDuration = RetentionPolicyManager.DayDuration(numberOfDays = 7),
      repository = repository,
    )

    val expectedRetentionDeadline = currentTimeMillis - (7L * 24 * 60 * 60 * 1000)

    sevenDayManager.processDataRetention()

    verifySuspend {
      repository.deleteAllOlderThan(Instant.fromEpochMilliseconds(expectedRetentionDeadline))
    }
  }

  @Test
  fun `processDataRetention should handle repository errors`() = runTest {
    everySuspend { repository.deleteAllOlderThan(any()) } throws RuntimeException("Database error")

    assertFailsWith<RuntimeException> {
      manager.processDataRetention()
    }
  }
}

class TestClock : Clock {
  private var currentInstant: Instant = Clock.System.now()

  fun setCurrentTime(epochMilliseconds: Long) {
    currentInstant = Instant.fromEpochMilliseconds(epochMilliseconds)
  }

  override fun now(): Instant = currentInstant
}
