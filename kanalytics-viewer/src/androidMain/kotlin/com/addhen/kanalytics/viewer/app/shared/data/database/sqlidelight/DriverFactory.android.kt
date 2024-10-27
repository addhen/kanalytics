package com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight

import app.cash.sqldelight.db.SqlDriver
import com.addhen.kanalytics.viewer.android.ContextInitializer

internal actual object DriverFactory {

    actual fun createDbDriver(): SqlDriver = AndroidSqlDriverFactory(
      applicationContext = ContextInitializer.applicationContext
    ).createDriver()
}
