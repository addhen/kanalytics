package com.addhen.kanalytics.viewer.app.shared.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

public interface EventRepository {

  public fun getAll(pagingConfig: PagingConfig): Flow<PagingData<EventData>>

  public suspend fun insert(eventData: EventData)

  public suspend fun deleteAll()

  public suspend fun deleteAllOlderThan(date: Instant)
}
