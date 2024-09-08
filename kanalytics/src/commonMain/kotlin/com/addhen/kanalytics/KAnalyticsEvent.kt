// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.kanalytics

private const val DEFAULT_EVENT_PARAM_VALUE = "NA"

public data class KAnalyticsEvent(
  val eventName: String,
  val eventDescription: String = DEFAULT_EVENT_PARAM_VALUE,
  val parameters: MutableMap<String, Any?> = mutableMapOf(),
) {

  public fun addParameter(parameterName: String, value: Any?): KAnalyticsEvent = apply {
    parameters[parameterName] = value
  }

  public fun addParameters(map: Map<String, Any?>): KAnalyticsEvent = apply {
    parameters.putAll(map)
  }
}
