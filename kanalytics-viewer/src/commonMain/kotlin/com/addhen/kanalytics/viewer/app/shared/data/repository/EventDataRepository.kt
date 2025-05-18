// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.data.repository

import com.addhen.kanalytics.viewer.app.shared.data.database.EventDataDao
import com.addhen.kanalytics.viewer.app.shared.data.database.entities.EventDataEntity
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

public class EventDataRepository(private val eventDataDao: EventDataDao) : EventRepository {

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

  override suspend fun deleteAll() {
    eventDataDao.deleteAll().await()
  }

  override suspend fun deleteAllOlderThan(date: Instant) {
    eventDataDao.deleteAllOlderThan(date).await()
  }

  override fun getAll(): Flow<List<EventData>> = eventDataDao.getEvents().map { eventDataEntities ->
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

  override fun search(query: String): Flow<List<EventData>> =
    eventDataDao.search(query).map { eventDataEntities ->
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

  override fun getEventById(id: Long): Flow<EventData> = eventDataDao.getEventById(id)
    .map { eventDataEntity ->
      EventData(
        id = eventDataEntity.id,
        name = eventDataEntity.name,
        trackerName = eventDataEntity.trackerName,
        description = eventDataEntity.description,
        createdAt = eventDataEntity.createdAt,
        properties = eventDataEntity.properties,
      )
    }

  internal companion object {
    val Instance by lazy { EventDataRepository(eventDataDao = EventDataDao.Instance) }
  }
}
