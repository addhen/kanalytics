package com.addhen.kanalytics.viewer.app.shared.data.repository

import com.addhen.kanalytics.viewer.app.shared.data.database.EventDataDao
import com.addhen.kanalytics.viewer.app.shared.data.database.entities.EventDataEntity
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class EventDataRepository(
  private val eventDataDao: EventDataDao
) {

  fun getAll(limit: Long, offset: Long): Flow<List<EventData>> {
    return eventDataDao.getAll(limit, offset).map { list ->
      list.map { eventDataEntity ->
        EventData(
          id = eventDataEntity.id,
          name = eventDataEntity.name,
          description = eventDataEntity.description,
          createdAt = eventDataEntity.createdAt,
          properties = eventDataEntity.properties,
        )
      }
    }
  }

  suspend fun insert(eventData: EventData) {
    eventDataDao.insert(
      EventDataEntity(
        id = eventData.id,
        name = eventData.name,
        description = eventData.description,
        createdAt = eventData.createdAt,
        properties = eventData.properties
      )
    )
  }

  suspend fun deleteAll(): Unit = eventDataDao.deleteAll()
}
