package com.addhen.kanalytics.viewer.app.shared.data.database.entities

import kotlinx.datetime.Instant

internal class EventDataEntity(
  val id: Long,
  val name: String,
  val provider: String,
  val description: String?,
  val createdAt: Instant,
  val properties: Map<String, Any>,
)
