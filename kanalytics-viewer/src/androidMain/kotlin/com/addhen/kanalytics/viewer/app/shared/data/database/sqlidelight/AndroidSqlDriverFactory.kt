// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

internal class AndroidSqlDriverFactory(
  private val applicationContext: Context,
) : SqlDriverFactory {

  override fun createDriver(): SqlDriver {
    return AndroidSqliteDriver(
      EventViewerDatabase.Schema,
      applicationContext,
      Constants.DB_NAME,
    )
  }
}
