// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.cash.paging.map
import com.addhen.kanalytics.viewer.app.shared.data.database.EventDataDao
import com.addhen.kanalytics.viewer.app.shared.data.database.entities.EventDataEntity
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class EventDataRepository(
  private val eventDataDao: EventDataDao,
) {

  fun getAll(pagingConfig: PagingConfig): Flow<PagingData<EventData>> = Pager(
    config = pagingConfig,
    pagingSourceFactory = { eventDataDao.getAll() },
  ).flow.map { pagingData ->
    pagingData.map { eventDataEntity ->
      EventData(
        id = eventDataEntity.id,
        name = eventDataEntity.name,
        provider = eventDataEntity.provider,
        description = eventDataEntity.description,
        createdAt = eventDataEntity.createdAt,
        properties = eventDataEntity.properties,
      )
    }
  }

  suspend fun insert(eventData: EventData) {
    eventDataDao.insert(
      EventDataEntity(
        id = eventData.id,
        name = eventData.name,
        provider = eventData.provider,
        description = eventData.description,
        createdAt = eventData.createdAt,
        properties = eventData.properties,
      ),
    )
  }

  suspend fun deleteAll(): Unit = eventDataDao.deleteAll()

  companion object {
    val Instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
      EventDataRepository(
        eventDataDao = EventDataDao.Instance,
      )
    }
  }
}
