// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer

import com.addhen.kanalytics.KAnalyticsEvent
import com.addhen.kanalytics.TrackerName
import com.addhen.kanalytics.viewer.app.NotificationManager
import com.addhen.kanalytics.viewer.app.shared.AppCoroutineDispatchers
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.data.repository.EventDataRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant

public class KAnalyticsCollector(
  private val repository: EventDataRepository = EventDataRepository.Instance,
  private val showNotification: Boolean = true,
  duration: RetentionPolicyManager.DayDuration = RetentionPolicyManager.DayDuration(7),
  private val notificationManager: NotificationManager = NotificationManager(),
  private val appCoroutineDispatchers: AppCoroutineDispatchers = AppCoroutineDispatchers(),
) {
  private val scope = MainScope()
  private val retentionPolicyManager: RetentionPolicyManager = RetentionPolicyManager(
    dayDuration = duration,
    repository = repository,
  )

  public fun onEventSent(
    kAnalyticsEvent: KAnalyticsEvent,
    trackerName: TrackerName,
    onSentDate: Instant
  ) {
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

      if (showNotification) {
        notificationManager.showNotification(
          eventName = kAnalyticsEvent.eventName,
          trackerName = trackerName.value,
        )
      }

      withContext(appCoroutineDispatchers.io) {
        retentionPolicyManager.processDataRetention()
      }
    }
  }

  internal companion object {

    fun getInstance(
      showNotification: Boolean,
      duration: RetentionPolicyManager.DayDuration,
    ): KAnalyticsCollector {
      return KAnalyticsCollector(
        duration = duration,
        showNotification = showNotification,
        repository = EventDataRepository.Instance
      )
    }
  }
}
