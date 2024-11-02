// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight

import app.cash.sqldelight.db.SqlDriver
import com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight.adapters.instantAdapter
import com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight.adapters.mapAdapter

internal interface SqlDriverFactory {
  fun createDriver(): SqlDriver
}

internal expect object DriverFactory {
  fun createDbDriver(): SqlDriver
}

internal fun createDatabase(): EventViewerDatabase {
  return EventViewerDatabase(
    driver = DriverFactory.createDbDriver(),
    event_dataAdapter = Event_data.Adapter(
      event_propertiesAdapter = mapAdapter,
      event_created_dateAdapter = instantAdapter,
    ),
  )
}

internal object Constants {
  const val DB_NAME = "event_viewer.db"
}
