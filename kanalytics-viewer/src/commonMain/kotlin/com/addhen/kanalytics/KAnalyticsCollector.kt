// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

import com.addhen.kanalytics.viewer.app.NotificationManager
import com.addhen.kanalytics.viewer.app.shared.AppCoroutineDispatchers
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.data.repository.EventDataRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant

internal class KAnalyticsCollector(
  private val repository: EventDataRepository = EventDataRepository.Instance,
  private val retentionPolicyManager: RetentionPolicyManager = RetentionPolicyManager(
    dayDuration = RetentionPolicyManager.DayDuration(numberOfDays = 7),
    repository = repository,
  ),
  private val notificationManager: NotificationManager = NotificationManager(),
  private val appCoroutineDispatchers: AppCoroutineDispatchers = AppCoroutineDispatchers(),
) {
  private val scope = MainScope()

  fun onEventSent(kAnalyticsEvent: KAnalyticsEvent, trackerName: TrackerName, onSentDate: Instant) {
    scope.launch {
      withContext(appCoroutineDispatchers.io) {
        repository.insert(
          eventData = EventData(
            id = null,
            name = kAnalyticsEvent.eventName,
            trackerName = trackerName.value,
            description = kAnalyticsEvent.eventDescription,
            createdAt = onSentDate,
            properties = kAnalyticsEvent.properties.mapValues { it.value ?: "" },
          ),
        )
      }

      notificationManager.showNotification(
        eventName = kAnalyticsEvent.eventName,
        trackerName = trackerName.value,
      )

      withContext(appCoroutineDispatchers.io) {
        retentionPolicyManager.processDataRetention()
      }
    }
  }

  companion object {
    val instance: KAnalyticsCollector by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
      KAnalyticsCollector(
        repository = EventDataRepository.Instance,
      )
    }
  }
}
