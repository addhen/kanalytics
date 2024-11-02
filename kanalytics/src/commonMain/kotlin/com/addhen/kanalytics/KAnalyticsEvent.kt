// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

private const val DEFAULT_EVENT_PARAM_VALUE = "NA"

public class KAnalyticsEvent(
  public val eventName: String,
  public val eventDescription: String = DEFAULT_EVENT_PARAM_VALUE,
  public val properties: MutableMap<String, Any?> = mutableMapOf(),
) {

  public fun addParameter(parameterName: String, value: Any?): KAnalyticsEvent = apply {
    properties[parameterName] = value
  }

  public fun addParameters(map: Map<String, Any?>): KAnalyticsEvent = apply {
    properties.putAll(map)
  }

  public fun copy(
    eventName: String = this.eventName,
    eventDescription: String = this.eventDescription,
    parameters: MutableMap<String, Any?> = this.properties,
  ): KAnalyticsEvent = KAnalyticsEvent(eventName, eventDescription, parameters)

  override fun toString(): String {
    return "KAnalyticsEvent(" +
      "eventName='$eventName', " +
      "eventDescription='$eventDescription', " +
      "properties=$properties" +
      ")"
  }
}
