// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.jvm.JvmName
import kotlinx.coroutines.delay

@Composable
public fun ViewerAppTheme(
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
  onStopClick: () -> Unit,
) {
  Column(
    modifier = Modifier.padding(16.dp).fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Text("Current Location: $currentLocation")
    Text("Last known location: $lastKnownLocation")
    HorizontalDivider(thickness = 2.dp)
    Button(onClick = { onStopClick() }) {
      Text("Stop")
    }
    composeLastKnowLocationState(currentLocation, lastKnownLocation)
  }
}

@Composable
private fun composeLastKnowLocationState(currentLocation: String, lastKnownLocation: String) {
  val hasLastKnowLocation = currentLocation.isNotEmpty() || lastKnownLocation.isNotEmpty()

  var visibility by remember { mutableStateOf(false) }

  AnimatedVisibility(
    visible = visibility,
    enter = expandVertically(),
    exit = shrinkVertically(),
  ) {
    CurrentLocationBox(
      currentLocation,
      lastKnownLocation,
    )
  }

  LaunchedEffect(hasLastKnowLocation) {
    if (hasLastKnowLocation) {
      visibility = true
    } else {
      delay(2000)
      visibility = false
    }
  }
}

@Composable
private fun CurrentLocationBox(currentLocation: String, lastKnownLocation: String) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 8.dp, bottom = 8.dp),
  ) {
    Column {
      Text("With klocation-compose", fontSize = 20.sp, fontWeight = FontWeight.Bold)
      Text("Current Location: $currentLocation")
      Text("Last known location: $lastKnownLocation")
    }
  }
}
