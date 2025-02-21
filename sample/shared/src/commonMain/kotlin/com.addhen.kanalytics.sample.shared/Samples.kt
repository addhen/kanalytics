// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.shared

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

public const val TRIGGER_ANALYTICS_EVENT_TEST_TAG: String = "trigger_analytics_event_test_tag"
public const val EVENT_VIEWER_TEST_TAG: String = "event_viewer_test_tag"

@Composable
public fun SampleTheme(
  useDarkColors: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = if (useDarkColors) darkColorScheme() else lightColorScheme(),
    content = content,
  )
}

@Composable
public fun Sample(
  modifier: Modifier,
  onTriggerAnalytics: () -> Unit,
  onTriggerAirshipAnalytics: () -> Unit,
  onTriggerAmplitudeAnalytics: () -> Unit,
  onTriggerFirebaseAnalytics: () -> Unit,
  onEventViewer: () -> Unit
) {
  Column(
    modifier = modifier.padding(16.dp).fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Text("Sample app showing how to use kanalytics")
    HorizontalDivider(thickness = 2.dp)
    Button(
      onClick = onTriggerAirshipAnalytics,
      modifier = Modifier.testTag(TRIGGER_ANALYTICS_EVENT_TEST_TAG),
    ) {
      Text("Send analytics event to Airship tracker")
    }

    Button(
      onClick = onTriggerAmplitudeAnalytics,
      modifier = Modifier.testTag(TRIGGER_ANALYTICS_EVENT_TEST_TAG),
    ) {
      Text("Send analytics event to Amplitude tracker")
    }

    Button(
      onClick = onTriggerFirebaseAnalytics,
      modifier = Modifier.testTag(TRIGGER_ANALYTICS_EVENT_TEST_TAG),
    ) {
      Text("Send analytics event to Firebase tracker")
    }

    Button(
      onClick = onTriggerAnalytics,
      modifier = Modifier.testTag(TRIGGER_ANALYTICS_EVENT_TEST_TAG),
    ) {
      Text("Send analytics event to all trackers")
    }

    Button(
      onClick = onEventViewer,
      modifier = Modifier.testTag(EVENT_VIEWER_TEST_TAG),
    ) {
      Text("Start viewer")
    }
  }
}
