// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics

/**
 * Represents an event to be tracked by [KAnalytics].
 *
 * @param eventName The name of the event.
 * @param eventDescription An optional description of the event.
 * @param properties A map of properties associated with the event.
 */
public class KAnalyticsEvent(
  public val eventName: String,
  public val eventDescription: String? = null,
  public val properties: MutableMap<String, Any?> = mutableMapOf(),
) {

  /**
   * Adds a parameter to the event.
   * @param parameterName The name of the parameter.
   * @param value The value of the parameter.
   */
  public fun addParameter(parameterName: String, value: Any?): KAnalyticsEvent = apply {
    properties[parameterName] = value
  }

  /**
   * Adds multiple parameters to the event.
   *
   * @param map A map of parameters to add.
   */
  public fun addParameters(map: Map<String, Any?>): KAnalyticsEvent = apply {
    properties.putAll(map)
  }

  /**
   * Creates a copy of the event with the specified properties.
   *
   * @param eventName The name of the event.
   * @param eventDescription The description of the event.
   * @param parameters The parameters of the event.
   */
  public fun copy(
    eventName: String = this.eventName,
    eventDescription: String? = this.eventDescription,
    parameters: MutableMap<String, Any?> = this.properties,
  ): KAnalyticsEvent = KAnalyticsEvent(eventName, eventDescription, parameters)

  /**
   * Returns a string representation of the event.
   */
  override fun toString(): String = "KAnalyticsEvent(" +
    "eventName='$eventName', " +
    "eventDescription='$eventDescription', " +
    "properties=$properties" +
    ")"
}
