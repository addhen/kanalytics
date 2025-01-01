package com.addhen.kanalytics.viewer.app.shared.data

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

internal fun Map<String, Any>.prettyPrintJson(): String {
  return try {
    Json.encodeToString(JsonElement.serializer(), this.toJsonElement())
  } catch (ignore: Exception) {
    ignore.printStackTrace()
    ""
  }
}

private fun Collection<*>.toJsonElement(): JsonElement {
  val list: MutableList<JsonElement> = mutableListOf()
  this.forEach { value ->
    when (value) {
      null -> list.add(JsonNull)
      is Map<*, *> -> list.add(value.toJsonElement())
      is List<*> -> list.add(value.toJsonElement())
      is Boolean -> list.add(JsonPrimitive(value))
      is Number -> list.add(JsonPrimitive(value))
      is String -> list.add(JsonPrimitive(value))
      is Enum<*> -> list.add(JsonPrimitive(value.toString()))
      else -> throw IllegalStateException("Can't serialize unknown collection type: $value")
    }
  }
  return JsonArray(list)
}

private fun Map<*, *>.toJsonElement(): JsonElement {
  val map: MutableMap<String, JsonElement> = mutableMapOf()
  this.forEach { (key, value) ->
    key as String
    when (value) {
      null -> map[key] = JsonNull
      is Map<*, *> -> map[key] = value.toJsonElement()
      is List<*> -> map[key] = value.toJsonElement()
      is Boolean -> map[key] = JsonPrimitive(value)
      is Number -> map[key] = JsonPrimitive(value)
      is String -> map[key] = JsonPrimitive(value)
      is Enum<*> -> map[key] = JsonPrimitive(value.toString())
      else -> throw IllegalStateException("Can't serialize unknown type: $value")
    }
  }
  return JsonObject(map)
}
