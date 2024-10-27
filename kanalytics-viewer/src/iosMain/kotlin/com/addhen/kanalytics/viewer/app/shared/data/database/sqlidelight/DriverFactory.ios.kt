package com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight

import app.cash.sqldelight.db.SqlDriver

internal actual object DriverFactory {
    actual fun createDbDriver(): SqlDriver = IosSqlDriverFactory.createDriver()
}
