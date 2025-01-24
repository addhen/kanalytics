// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.internal

import com.addhen.kanalytics.Interceptor
import com.addhen.kanalytics.KAnalyticsEvent
import com.addhen.kanalytics.TrackerName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class DefaultInterceptorChainTest {

  private val fakeInterceptor = Interceptor { chain -> chain.proceed(chain.event) }

  @Test
  fun `should call interceptor once`() {
    val event = KAnalyticsEvent("testEvent")
    val chain = DefaultInterceptorChain(
      interceptors = listOf(fakeInterceptor),
      index = 0,
      trackerName = TrackerName("testTracker"),
      event = event,
    )

    val result = chain.proceed(event)

    assertEquals(event, result)
  }

  @Test
  fun `should throw exception when proceed is called more than once`() {
    val event = KAnalyticsEvent("testEvent")
    val chain = DefaultInterceptorChain(
      interceptors = listOf(fakeInterceptor),
      index = 0,
      trackerName = TrackerName("testTracker"),
      event = event,
    )

    chain.proceed(event)

    assertFailsWith<IllegalStateException> {
      chain.proceed(event)
    }
  }

  @Test
  fun `should return event when index exceeds interceptors size`() {
    val event = KAnalyticsEvent("testEvent")
    val chain = DefaultInterceptorChain(
      interceptors = emptyList(),
      index = 0,
      trackerName = TrackerName("testTracker"),
      event = event,
    )

    val result = chain.proceed(event)

    assertEquals(event, result)
  }

  @Test
  fun `should create new chain with updated values`() {
    val event = KAnalyticsEvent("testEvent")
    val chain = DefaultInterceptorChain(
      interceptors = listOf(fakeInterceptor),
      index = 0,
      trackerName = TrackerName("testTracker"),
      event = event,
    )

    val newEvent = KAnalyticsEvent("newTestEvent")
    val newChain = chain.copy(index = 1, event = newEvent)
    val expectedChain =
      DefaultInterceptorChain(listOf(fakeInterceptor), 1, TrackerName("testTracker"), newEvent)

    assertEquals(expectedChain.toString(), newChain.toString())
  }
}
