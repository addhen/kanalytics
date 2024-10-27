package com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight

import app.cash.sqldelight.db.SqlDriver

public interface SqlDriverFactory {
  public fun createDriver(): SqlDriver
}
