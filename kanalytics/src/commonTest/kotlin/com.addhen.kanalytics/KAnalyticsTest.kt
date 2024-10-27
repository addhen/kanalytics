// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

private const val SCREEN_NAME = "screenName"
private const val EVENT_NAME = "testEvent"
private const val KEY_ONE = "eventKey1"
private const val KEY_TWO = "eventKey2"
private const val VALUE_ONE = "eventValue1"
private const val VALUE_TWO = "eventValue2"

class KAnalyticsTest {

  private val firebaseTracker = FirebaseTracker()
  private val adjustTracker = AdjustTracker()
  private val mockTrackerIntercept = MockTrackerIntercept()

  private lateinit var kanalytics: KAnalytics

  @BeforeTest
  fun setUp() {
    kanalytics = KAnalytics.Builder()
      .addTracker(firebaseTracker)
      .addTracker(adjustTracker)
      .build()
  }

  @Test
  fun `should send event to all trackers`() {
    val event = KAnalyticsEvent(EVENT_NAME).apply {
      addParameters(
        mapOf(
          SCREEN_NAME to SCREEN_NAME,
          KEY_ONE to VALUE_ONE,
          KEY_TWO to VALUE_TWO,
        ),
      )
    }

    kanalytics.send(event)

    assertEquals(1, firebaseTracker.analyticsEvents.size)
    assertEquals(1, adjustTracker.analyticsEvents.size)
    assertNull(mockTrackerIntercept.event)
    assertHasEvents(firebaseTracker.analyticsEvents)
    assertHasEvents(adjustTracker.analyticsEvents)
  }

  @Test
  fun `should send event to specific tracker`() {
    val event = KAnalyticsEvent(EVENT_NAME).apply {
      addParameter(SCREEN_NAME, SCREEN_NAME)
      addParameter(KEY_ONE, VALUE_ONE)
      addParameter(KEY_TWO, VALUE_TWO)
    }

    kanalytics.send(event, FirebaseTracker::class)

    assertEquals(0, adjustTracker.analyticsEvents.size)
    assertEquals(1, firebaseTracker.analyticsEvents.size)
    assertNull(mockTrackerIntercept.event)
    assertHasEvents(firebaseTracker.analyticsEvents)
  }

  @Test
  fun `should send event with parameters`() {
    val event = KAnalyticsEvent(EVENT_NAME).apply {
      addParameter(SCREEN_NAME, SCREEN_NAME)
      addParameter(KEY_ONE, VALUE_ONE)
      addParameter(KEY_TWO, VALUE_TWO)
    }

    kanalytics.send(event)

    assertEquals(1, firebaseTracker.analyticsEvents.size)
    assertEquals(1, adjustTracker.analyticsEvents.size)
    assertNull(mockTrackerIntercept.event)
    assertEvents(firebaseTracker.analyticsEvents)
    assertEvents(adjustTracker.analyticsEvents)
  }

  @Test
  fun `should send event with parameters to specific tracker`() {
    val event = KAnalyticsEvent(EVENT_NAME).apply {
      addParameter(SCREEN_NAME, SCREEN_NAME)
      addParameter(KEY_ONE, VALUE_ONE)
      addParameter(KEY_TWO, VALUE_TWO)
    }

    kanalytics.send(event, AdjustTracker::class)

    assertNull(mockTrackerIntercept.event)
    assertEquals(1, adjustTracker.analyticsEvents.size)
    assertEquals(0, firebaseTracker.analyticsEvents.size)
  }

  @Test
  fun `should send event to all trackers and interceptors`() {
    kanalytics = KAnalytics.Builder()
      .addTracker(firebaseTracker)
      .addTracker(adjustTracker)
      .addInterceptor(mockTrackerIntercept)
      .build()

    val event = KAnalyticsEvent(EVENT_NAME).apply {
      addParameters(
        mapOf(
          SCREEN_NAME to SCREEN_NAME,
          KEY_ONE to VALUE_ONE,
          KEY_TWO to VALUE_TWO,
        ),
      )
    }

    kanalytics.send(event)

    assertEquals(1, firebaseTracker.analyticsEvents.size)
    assertEquals(1, adjustTracker.analyticsEvents.size)
    assertEquals(event, mockTrackerIntercept.event)
    assertEquals(3, mockTrackerIntercept.event?.properties?.size)
    assertHasEvents(firebaseTracker.analyticsEvents)
    assertHasEvents(adjustTracker.analyticsEvents)
  }

  private fun assertHasEvent(event: KAnalyticsEvent) {
    assertEquals(EVENT_NAME, event.eventName)
    assertEquals(3, event.properties.size)
    assertEquals(SCREEN_NAME, event.properties[SCREEN_NAME])
    assertEquals(VALUE_ONE, event.properties[KEY_ONE])
    assertEquals(VALUE_TWO, event.properties[KEY_TWO])
  }

  private fun assertHasEvents(events: List<KAnalyticsEvent>) {
    assertTrue(events.isNotEmpty())
    events.forEach { event -> assertHasEvent(event) }
  }

  private fun assertEvents(events: List<KAnalyticsEvent>) {
    assertTrue(events.isNotEmpty())
    events.forEach { event ->
      assertEquals(EVENT_NAME, event.eventName)
      assertEquals(SCREEN_NAME, event.properties[SCREEN_NAME])
      assertEquals(3, event.properties.size)
      assertEquals(VALUE_ONE, event.properties[KEY_ONE])
    }
  }

  inner class FirebaseTracker : Tracker {

    val analyticsEvents = mutableListOf<KAnalyticsEvent>()

    override fun send(event: KAnalyticsEvent) {
      // Send event to firebase
      analyticsEvents.add(event)
    }
  }

  inner class AdjustTracker : Tracker {

    val analyticsEvents = mutableListOf<KAnalyticsEvent>()

    override fun send(event: KAnalyticsEvent) {
      // Send event to adjust
      analyticsEvents.add(event)
    }
  }

  inner class MockTrackerIntercept : Interceptor {

    var event: KAnalyticsEvent? = null

    override fun intercept(event: KAnalyticsEvent) {
      this.event = event
    }
  }
}
