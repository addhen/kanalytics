// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

@file:OptIn(kotlin.time.ExperimentalTime::class)

package com.addhen.kanalytics.viewer.app.shared.data.model

import kotlin.time.Instant

public class EventData(
  public val id: Long?,
  public val name: String,
  public val trackerName: String,
  public val description: String?,
  public val createdAt: Instant,
  public val properties: Map<String, Any>,
)
