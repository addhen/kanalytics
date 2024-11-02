// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import com.addhen.kanalytics.viewer.app.shared.AppCoroutineDispatchers
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.data.repository.EventDataRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant

public class KAnalyticsCollector(
  private val repository: EventDataRepository = EventDataRepository.Instance,
  private val appCoroutineDispatchers: AppCoroutineDispatchers = AppCoroutineDispatchers(),
) {
  private val scope = MainScope()

  public fun onEventSent(
    kAnalyticsEvent: KAnalyticsEvent,
    eventProvider: String,
    onSentDate: Instant,
  ) {
    scope.launch {
      withContext(appCoroutineDispatchers.io) {
        repository.insert(
          eventData = EventData(
            id = null,
            name = kAnalyticsEvent.eventName,
            trackerName = eventProvider,
            description = kAnalyticsEvent.eventDescription,
            createdAt = onSentDate,
            properties = kAnalyticsEvent.properties.mapValues { it.value ?: "" },
          ),
        )
      }
    }
  }

  public companion object {
    public val Instance: KAnalyticsCollector by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
      KAnalyticsCollector(
        repository = EventDataRepository.Instance,
      )
    }
  }
}
