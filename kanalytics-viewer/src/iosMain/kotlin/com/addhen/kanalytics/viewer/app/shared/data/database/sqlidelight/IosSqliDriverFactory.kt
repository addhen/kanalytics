package com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

internal object IosSqlDriverFactory : SqlDriverFactory {

  override fun createDriver(): SqlDriver =
    NativeSqliteDriver(
      EventViewerDatabase.Schema,
      Constants.DB_NAME,
      maxReaderConnections = 4,
    )
}
