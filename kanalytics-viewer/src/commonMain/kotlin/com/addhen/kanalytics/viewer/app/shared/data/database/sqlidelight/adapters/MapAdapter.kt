package com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight.adapters

import app.cash.sqldelight.ColumnAdapter

internal val mapAdapter = object : ColumnAdapter<Map<String, Any>, String> {

  override fun decode(databaseValue: String): Map<String, Any> {
    return databaseValue.split(",").associate {
      val (key, value) = it.split("=")
      key to value
    }
  }

  override fun encode(value: Map<String, Any>): String {
    return value.map { (key, value) ->
      "$key=$value"
    }.joinToString(",")
  }
}
