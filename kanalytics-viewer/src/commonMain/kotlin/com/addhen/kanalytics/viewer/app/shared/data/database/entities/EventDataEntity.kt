// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.data.database.entities

internal class EventDataEntity(
  val id: Long?,
  val name: String,
  val trackerName: String,
  val description: String?,
  val createdAt: kotlin.time.Instant,
  val properties: Map<String, Any>,
)
