// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.data.repository

import com.addhen.kanalytics.viewer.app.shared.data.database.EventDataDao
import com.addhen.kanalytics.viewer.app.shared.data.database.entities.EventDataEntity
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

internal class EventDataRepository(
  private val eventDataDao: EventDataDao,
) : EventRepository {

  override suspend fun insert(eventData: EventData) {
    eventDataDao.insert(
      EventDataEntity(
        id = eventData.id,
        name = eventData.name,
        trackerName = eventData.trackerName,
        description = eventData.description,
        createdAt = eventData.createdAt,
        properties = eventData.properties,
      ),
    )
  }

  override suspend fun deleteAll(): Unit = eventDataDao.deleteAll()

  override suspend fun deleteAllOlderThan(date: Instant): Unit =
    eventDataDao.deleteAllOlderThan(date)

  override fun getAll(): Flow<List<EventData>> {
    return eventDataDao.getEvents().map { eventDataEntities ->
      eventDataEntities.map { eventDataEntity ->
        EventData(
          id = eventDataEntity.id,
          name = eventDataEntity.name,
          trackerName = eventDataEntity.trackerName,
          description = eventDataEntity.description,
          createdAt = eventDataEntity.createdAt,
          properties = eventDataEntity.properties,
        )
      }
    }
  }

  override fun search(query: String): Flow<List<EventData>> {
    return eventDataDao.search(query).map { eventDataEntities ->
      eventDataEntities.map { eventDataEntity ->
        EventData(
          id = eventDataEntity.id,
          name = eventDataEntity.name,
          trackerName = eventDataEntity.trackerName,
          description = eventDataEntity.description,
          createdAt = eventDataEntity.createdAt,
          properties = eventDataEntity.properties,
        )
      }
    }
  }

  override fun getEventById(id: Long): Flow<EventData> {
    return eventDataDao.getEventById(id)
      .map { eventDataEntity ->
        EventData(
          id = eventDataEntity.id,
          name = eventDataEntity.name,
          trackerName = eventDataEntity.trackerName,
          description = eventDataEntity.description,
          createdAt = eventDataEntity.createdAt,
          properties = eventDataEntity.properties
        )
      }
  }

  internal companion object {
    val Instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
      EventDataRepository(
        eventDataDao = EventDataDao.Instance,
      )
    }
  }
}
