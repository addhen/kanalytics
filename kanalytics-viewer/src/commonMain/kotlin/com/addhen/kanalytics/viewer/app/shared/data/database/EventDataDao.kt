package com.addhen.kanalytics.viewer.app.shared.data.database

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.addhen.kanalytics.viewer.app.shared.AppCoroutineDispatchers
import com.addhen.kanalytics.viewer.app.shared.data.database.entities.EventDataEntity
import com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight.EventViewerDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class EventDataDao(
  private val database: EventViewerDatabase,
  private val appCoroutineDispatchers: AppCoroutineDispatchers
) {

  fun getAll(limit: Long, offset: Long): Flow<List<EventDataEntity>> {
    return database.event_dataQueries.getAllEventData(limit, offset).asFlow()
      .mapToList(appCoroutineDispatchers.io).map { list ->
        list.map { eventDataEntity ->
          EventDataEntity(
            id = eventDataEntity.id,
            name = eventDataEntity.event_name,
            description = eventDataEntity.event_description,
            createdAt = eventDataEntity.event_created_date,
            properties = eventDataEntity.event_properties
          )
        }
      }.flowOn(appCoroutineDispatchers.databaseRead)
  }

  suspend fun insert(eventData: EventDataEntity) =
    withContext(appCoroutineDispatchers.databaseWrite) {
      database.event_dataQueries.insertEventData(
        event_name = eventData.name,
        event_description = eventData.description,
        event_created_date =  eventData.createdAt,
        event_properties = eventData.properties
    )
  }

  suspend fun deleteAll() = withContext(appCoroutineDispatchers.databaseWrite) {
    database.event_dataQueries.deleteAllEventData()
  }
}
