// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer

import com.addhen.kanalytics.KAnalyticsEvent
import com.addhen.kanalytics.KTrackerName
import com.addhen.kanalytics.viewer.app.NotificationManager
import com.addhen.kanalytics.viewer.app.shared.AppCoroutineDispatchers
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.data.repository.EventRepository
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@OptIn(ExperimentalCoroutinesApi::class)
class KAnalyticsCollectorTest {
  private val mainDispatcher: TestDispatcher = UnconfinedTestDispatcher()
  private val duration: RetentionPolicyManager.DayDuration = RetentionPolicyManager.DayDuration(7)
  private lateinit var repository: TestEventRepository
  private lateinit var dispatchers: AppCoroutineDispatchers
  private lateinit var notificationManager: TestNotificationManager
  private lateinit var shortcutManager: TestShortcutManager
  private lateinit var collector: KAnalyticsCollector

  @BeforeTest
  fun setup() {
    Dispatchers.setMain(mainDispatcher)
    repository = TestEventRepository()
    dispatchers = AppCoroutineDispatchers(
      io = mainDispatcher,
      main = mainDispatcher,
      default = mainDispatcher,
    )
    shortcutManager = TestShortcutManager()
    notificationManager = TestNotificationManager()
  }

  @AfterTest
  fun tearDown() {
    notificationManager.clearBuffer()
    repository.insertedEvents.clear()
    Dispatchers.resetMain()
  }

  @Test
  fun `onEventSent should store event and process retention with notifications enabled`() =
    runTest(mainDispatcher) {
      collector = KAnalyticsCollector(
        showNotification = true,
        scope = this,
        appCoroutineDispatchers = dispatchers,
        shortcutManager = shortcutManager,
        repository = repository,
        duration = duration,
        notificationManager = notificationManager,
      )

      val event = KAnalyticsEvent(
        eventName = "test_event",
        eventDescription = "Test Description",
        properties = mutableMapOf("key" to "value"),
      )
      val trackerName = KTrackerName("test_tracker")
      val sentDate = Clock.System.now()

      collector.onEventSent(event, trackerName, sentDate)

      assertEquals(1, repository.insertedEvents.size)
      with(repository.insertedEvents.first()) {
        assertEquals("test_event", name)
        assertEquals(KTrackerName("test_tracker"), trackerName)
        assertEquals("Test Description", description)
        assertEquals(sentDate, createdAt)
        assertEquals(mapOf("key" to "value"), properties)
      }

      assertEquals(1, notificationManager.shownNotifications.size)
      val notification = notificationManager.shownNotifications.first()
      assertEquals("test_event - test_tracker", notification)
    }

  @Test
  fun `onEventSent should not show notifications when disabled`() = runTest(mainDispatcher) {
    collector = KAnalyticsCollector(
      showNotification = false,
      scope = this,
      appCoroutineDispatchers = dispatchers,
      shortcutManager = shortcutManager,
      repository = repository,
      duration = duration,
      notificationManager = notificationManager,
    )

    val event = KAnalyticsEvent(
      eventName = "test_event",
      eventDescription = "Test Description",
      properties = mutableMapOf("key" to "value"),
    )
    val trackerName = KTrackerName("test_tracker")
    val sentDate = Clock.System.now()

    collector.onEventSent(event, trackerName, sentDate)

    assertEquals(1, repository.insertedEvents.size)
    assertTrue(notificationManager.shownNotifications.isEmpty())
  }

  @Test
  fun `onEventSent should handle null property values`() = runTest(mainDispatcher) {
    collector = KAnalyticsCollector(
      showNotification = true,
      scope = this,
      appCoroutineDispatchers = dispatchers,
      shortcutManager = shortcutManager,
      repository = repository,
      duration = duration,
      notificationManager = notificationManager,
    )

    val event = KAnalyticsEvent(
      eventName = "test_event",
      eventDescription = "Test Description",
      properties = mutableMapOf("key" to null),
    )
    val trackerName = KTrackerName("test_tracker")
    val sentDate = Clock.System.now()

    collector.onEventSent(event, trackerName, sentDate)

    assertEquals(1, repository.insertedEvents.size)
    with(repository.insertedEvents.first()) {
      assertEquals(mapOf("key" to ""), properties)
    }
  }

  private inner class TestEventRepository : EventRepository {
    val insertedEvents = mutableListOf<EventData>()

    override suspend fun insert(eventData: EventData) {
      insertedEvents.add(eventData)
    }

    override suspend fun deleteAll() {
      // Not to be implemented
    }

    override suspend fun deleteAllOlderThan(date: Instant) {
      // Not to be implemented
    }

    override fun getAll(): Flow<List<EventData>> = flowOf(insertedEvents)

    override fun search(query: String): Flow<List<EventData>> {
      // Not to be implemented
      return flow { }
    }

    override fun getEventById(id: Long): Flow<EventData> {
      // Not to be implemented
      return flow { }
    }
  }

  private inner class TestNotificationManager : NotificationManager {
    val shownNotifications = mutableListOf<String>()

    override fun showNotification(eventName: String, trackerName: String) {
      shownNotifications.add("$eventName - $trackerName")
    }

    override fun clearBuffer() {
      shownNotifications.clear()
    }
  }

  class TestShortcutManager : ShortcutManager {
    var shortcutSetup: Boolean = false
      private set

    override fun setupShortcut(show: Boolean) {
      shortcutSetup = show
    }
  }
}
