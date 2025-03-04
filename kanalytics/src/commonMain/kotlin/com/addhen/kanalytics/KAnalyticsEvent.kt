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
  properties: Map<String, Any?> = emptyMap(),
  userProperties: Map<String, Any?> = emptyMap(),
) {

  private val _properties = properties.toMutableMap()
  private val _userProperties = userProperties.toMutableMap()

  public val properties: Map<String, Any?> = _properties
  public val userProperties: Map<String, Any?> = _userProperties

  /**
   * Adds a custom event property to the event.
   * @param propertyName The name of the property.
   * @param value The value of the property.
   */
  public fun addProperty(propertyName: String, value: Any?): KAnalyticsEvent = apply {
    _properties[propertyName] = value
  }

  /**
   * Adds custom event properties to the event.
   *
   * @param map A map of properties to add.
   */
  public fun addProperties(map: Map<String, Any?>): KAnalyticsEvent = apply {
    _properties.putAll(map)
  }

  /**
   * Adds user properties to the event.
   *
   * @param map A map of user properties to add.
   */
    public fun addUserProperties(map: Map<String, Any?>): KAnalyticsEvent = apply {
      _userProperties.putAll(map)
    }

  /**
   * Adds a user property to the event.

   * @param propertyName The name of the property.
   * @param value The value of the property.
   */
    public fun addUserProperty(propertyName: String, value: Any?): KAnalyticsEvent = apply {
      _userProperties[propertyName] = value
    }

  /**
   * Creates a copy of the event with the specified properties.
   *
   * @param eventName The name of the event.
   * @param eventDescription The description of the event.
   * @param properties The properties of the event.
   */
  public fun copy(
    eventName: String = this.eventName,
    eventDescription: String? = this.eventDescription,
    properties: Map<String, Any?> = this.properties,
    userProperties: Map<String, Any?> = this.userProperties,
  ): KAnalyticsEvent = KAnalyticsEvent(
    eventName,
    eventDescription,
    properties,
    userProperties
  )

  /**
   * Returns a string representation of the event.
   */
  override fun toString(): String = "KAnalyticsEvent(" +
    "eventName='$eventName', " +
    "eventDescription='$eventDescription', " +
    "properties=$properties" +
    "userProperties=$userProperties" +
    ")"
}
