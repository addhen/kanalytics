// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.data.database

import androidx.paging.PagingSource
import app.cash.sqldelight.paging3.QueryPagingSource
import com.addhen.kanalytics.viewer.app.shared.AppCoroutineDispatchers
import com.addhen.kanalytics.viewer.app.shared.data.database.entities.EventDataEntity
import com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight.EventViewerDatabase
import com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight.createDatabase
import kotlinx.coroutines.withContext

internal class EventDataDao(
  private val database: EventViewerDatabase,
  private val appCoroutineDispatchers: AppCoroutineDispatchers,
) {

  fun getAll(): PagingSource<Int, EventDataEntity> = QueryPagingSource(
    countQuery = database.event_dataQueries.countAllEventData(),
    transacter = database.event_dataQueries,
    context = appCoroutineDispatchers.io,
    queryProvider = { limit: Long, offset: Long ->
      database.event_dataQueries.getAllEventData(
        limit = limit,
        offset = offset,
      ) {
          id, name, provider, description, created_at, properties ->
        EventDataEntity(
          id = id,
          name = name,
          provider = provider,
          description = description,
          createdAt = created_at,
          properties = properties,
        )
      }
    },
  )

  suspend fun insert(eventData: EventDataEntity) =
    withContext(appCoroutineDispatchers.databaseWrite) {
      database.event_dataQueries.insertEventData(
        event_name = eventData.name,
        event_provider = eventData.provider,
        event_description = eventData.description,
        event_created_date = eventData.createdAt,
        event_properties = eventData.properties,
      )
    }

  suspend fun deleteAll() = withContext(appCoroutineDispatchers.databaseWrite) {
    database.event_dataQueries.deleteAllEventData()
  }

  companion object {
    val Instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
      EventDataDao(
        database = createDatabase(),
        appCoroutineDispatchers = AppCoroutineDispatchers(),
      )
    }
  }
}
