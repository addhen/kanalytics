package com.addhen.kanalytics.internal

import com.addhen.kanalytics.Interceptor
import com.addhen.kanalytics.KAnalyticsEvent
import com.addhen.kanalytics.TrackerName

internal class DefaultInterceptorChain(
  private val interceptors: List<Interceptor>,
  private val index: Int,
  override val trackerName: TrackerName,
  override val event: KAnalyticsEvent
): Interceptor.Chain {
  private var calls: Int = 0

  internal fun copy(
    index: Int = this.index,
    trackerName: TrackerName = this.trackerName,
    event: KAnalyticsEvent = this.event
  ) = DefaultInterceptorChain(interceptors, index, trackerName, event)

  override fun proceed(event: KAnalyticsEvent): KAnalyticsEvent {
    calls++
    check(calls == 1) {
      "interceptor ${interceptors[index - 1]} must call proceed() exactly once"
    }
    if (index >= interceptors.size) {
      return event
    }

    val nextChain = copy(index = index + 1, event = event)
    return interceptors[index].intercept(nextChain)
  }

}
