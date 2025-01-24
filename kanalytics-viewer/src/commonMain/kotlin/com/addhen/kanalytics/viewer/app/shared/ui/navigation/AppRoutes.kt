// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
internal data object EventViewerRoute

@Serializable
internal data class EventDetailsRoute(val eventId: Long, val eventName: String)

@Serializable
internal data object PreferencesRoute
