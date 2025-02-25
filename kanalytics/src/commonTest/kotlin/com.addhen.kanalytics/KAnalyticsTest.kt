// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
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
  private val mockTrackerIntercept = FakeTrackerIntercept()
  private val mockTrackerIntercept2 = FakeTrackerIntercept2()

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
      addProperties(
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
      addProperty(SCREEN_NAME, SCREEN_NAME)
      addProperty(KEY_ONE, VALUE_ONE)
      addProperty(KEY_TWO, VALUE_TWO)
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
      addProperty(SCREEN_NAME, SCREEN_NAME)
      addProperty(KEY_ONE, VALUE_ONE)
      addProperty(KEY_TWO, VALUE_TWO)
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
      addProperty(SCREEN_NAME, SCREEN_NAME)
      addProperty(KEY_ONE, VALUE_ONE)
      addProperty(KEY_TWO, VALUE_TWO)
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
      .addInterceptor(mockTrackerIntercept2)
      .build()

    val event = KAnalyticsEvent(EVENT_NAME).apply {
      addProperties(
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
    assertEquals(
      event.copy(eventName = "Intercepted ${event.eventName}").toString(),
      mockTrackerIntercept.event.toString(),
    )
    assertEquals(3, mockTrackerIntercept.event?.properties?.size)
    assertEquals(
      event.copy(eventName = "Mock2 Intercepted Intercepted ${event.eventName}").toString(),
      mockTrackerIntercept2.event.toString(),
    )
    assertEquals(3, mockTrackerIntercept2.event?.properties?.size)

    val firebaseEvent = firebaseTracker.analyticsEvents
    assertTrue(firebaseEvent.isNotEmpty())
    firebaseEvent.forEach {
      assertEquals("Mock2 Intercepted Intercepted $EVENT_NAME", it.eventName)
      assertEquals(SCREEN_NAME, it.properties[SCREEN_NAME])
      assertEquals(3, it.properties.size)
      assertEquals(VALUE_ONE, it.properties[KEY_ONE])
    }

    val adjustEvent = adjustTracker.analyticsEvents
    assertTrue(adjustEvent.isNotEmpty())
    adjustEvent.forEach {
      assertEquals("Mock2 Intercepted Intercepted $EVENT_NAME", it.eventName)
      assertEquals(SCREEN_NAME, it.properties[SCREEN_NAME])
      assertEquals(3, it.properties.size)
      assertEquals(VALUE_ONE, it.properties[KEY_ONE])
    }
  }

  @Test
  fun `should throw exception if no trackers are added`() {
    assertFailsWith<IllegalStateException>("At least one tracker must be added.") {
      KAnalytics.Builder().build()
    }
  }

  @Test
  fun `should throw exception if event name is empty`() {
    assertFailsWith<IllegalStateException>("Event name cannot be blank") {
      kanalytics.send(KAnalyticsEvent(""))
    }
  }

  @Test
  fun `should throw exception if event name is blank`() {
    assertFailsWith<IllegalStateException>("Event name cannot be blank") {
      kanalytics.send(KAnalyticsEvent("   "))
    }
  }

  @Test
  fun `should not send events when disabled`() {
    kanalytics = KAnalytics.Builder()
      .addTracker(firebaseTracker)
      .addTracker(adjustTracker)
      .addInterceptor(mockTrackerIntercept)
      .addInterceptor(mockTrackerIntercept2)
      .setEventSendingEnabled(false)
      .build()

    val event = KAnalyticsEvent(EVENT_NAME).apply {
      addProperties(
        mapOf(
          SCREEN_NAME to SCREEN_NAME,
          KEY_ONE to VALUE_ONE,
          KEY_TWO to VALUE_TWO,
        ),
      )
    }

    kanalytics.send(event)

    assertTrue(firebaseTracker.analyticsEvents.isEmpty())
    assertTrue(firebaseTracker.analyticsEvents.isEmpty())
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

  inner class FirebaseTracker : KTracker {

    val analyticsEvents = mutableListOf<KAnalyticsEvent>()

    override fun send(event: KAnalyticsEvent) {
      // Send event to firebase
      analyticsEvents.add(event)
    }
  }

  inner class AdjustTracker : KTracker {

    val analyticsEvents = mutableListOf<KAnalyticsEvent>()

    override fun send(event: KAnalyticsEvent) {
      // Send event to adjust
      analyticsEvents.add(event)
    }
  }

  inner class FakeTrackerIntercept : Interceptor {

    var event: KAnalyticsEvent? = null
    var tracker: KTrackerName? = null

    override fun intercept(chain: Interceptor.Chain): KAnalyticsEvent {
      this.event = chain.event.copy(eventName = "Intercepted ${chain.event.eventName}")
      this.tracker = chain.kTrackerName
      return chain.proceed(this.event!!)
    }
  }

  inner class FakeTrackerIntercept2 : Interceptor {

    var event: KAnalyticsEvent? = null
    var tracker: KTrackerName? = null

    override fun intercept(chain: Interceptor.Chain): KAnalyticsEvent {
      this.event = chain.event.copy(eventName = "Mock2 Intercepted ${chain.event.eventName}")
      this.tracker = chain.kTrackerName
      return chain.proceed(this.event!!)
    }
  }
}
