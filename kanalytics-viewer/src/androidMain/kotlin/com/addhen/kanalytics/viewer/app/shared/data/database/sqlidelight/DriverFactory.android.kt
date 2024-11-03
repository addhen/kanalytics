// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight

import app.cash.sqldelight.db.SqlDriver
import com.addhen.kanalytics.viewer.app.android.ContextInitializer

internal actual object DriverFactory {

  actual fun createDbDriver(): SqlDriver = AndroidSqlDriverFactory(
    applicationContext = ContextInitializer.applicationContext,
  ).createDriver()
}
