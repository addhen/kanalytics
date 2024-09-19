// Copyright 2024, Addhen Ltd and the k-location project contributors
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
import androidx.compose.ui.unit.dp

@Composable
public fun SamplesTheme(
  useDarkColors: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = if (useDarkColors) darkColorScheme() else lightColorScheme(),
    content = content,
  )
}

@Composable
public fun Samples(
  currentLocation: String,
  lastKnownLocation: String,
  onAnalytics: () -> Unit,
  onStopClick: () -> Unit
) {
  Column(
    modifier = Modifier.padding(16.dp).fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Text("Current Location: $currentLocation")
    Text("Last known location: $lastKnownLocation")
    HorizontalDivider(thickness = 2.dp)
    Button(onClick = { onStopClick() }) {
      Text("Start analytics")
    }

    Button(onClick = { onAnalytics() }) {
      Text("Start viewer")
    }
  }
}
