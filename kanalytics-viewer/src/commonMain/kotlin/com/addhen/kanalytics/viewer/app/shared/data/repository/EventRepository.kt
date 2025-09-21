// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.data.repository

import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import kotlin.time.Instant
import kotlinx.coroutines.flow.Flow

public interface EventRepository {

  public suspend fun insert(eventData: EventData)

  public suspend fun deleteAll()

  public suspend fun deleteAllOlderThan(date: Instant)

  public fun getAll(): Flow<List<EventData>>

  public fun search(query: String): Flow<List<EventData>>

  public fun getEventById(id: Long): Flow<EventData>
}
