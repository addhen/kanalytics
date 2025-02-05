// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight.adapters

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.Instant

internal val instantAdapter = object : ColumnAdapter<Instant, String> {

  override fun decode(databaseValue: String): Instant = Instant.parse(databaseValue)

  override fun encode(value: Instant): String = value.toString()
}
