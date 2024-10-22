// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.addhen.kanalytics.viewer.app.shared.ui.theme.AppTheme
import com.addhen.kanalytics.viewer.app.shared.ui.theme.ColorContrast
import kotlinx.coroutines.delay

@Composable
public fun ViewerAppTheme(
  colorContrast: ColorContrast = ColorContrast.Default,
  content: @Composable () -> Unit
) {
  AppTheme(colorContrast, content)
}

@Composable
public fun Samples(
  currentLocation: String,
  lastKnownLocation: String,
  onStopClick: () -> Unit,
) {
  AnalyticsEventsScreen()
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

public data class AnalyticsEvent(
  val name: String,
  val timestamp: String,
  val key: String,
  val provider: String,
  val keyValueMap: Map<String, String>
)

@Composable
public fun AnalyticsEventsScreen() {
  var expandedEventIndex by remember { mutableStateOf<Int?>(null) }
  var searchQuery by remember { mutableStateOf("") }

  val events = remember {
    listOf(
      AnalyticsEvent(
        "Button Click",
        "2023-10-21 14:30:22",
        "UIInteraction",
        "Google Analytics",
        mapOf(
          "buttonId" to "submit-form",
          "pageLocation" to "/checkout",
          "userType" to "registered"
        )
      ),
      AnalyticsEvent(
        "Page View",
        "2023-10-21 14:30:10",
        "Navigation",
        "Mixpanel",
        mapOf(
          "pageName" to "Home",
          "referrer" to "https://google.com",
          "deviceType" to "mobile"
        )
      ),
      AnalyticsEvent(
        "Form Submit",
        "2023-10-21 14:29:55",
        "UserAction",
        "Amplitude",
        mapOf(
          "formId" to "contact-us",
          "formCompletionTime" to "45s",
          "formErrors" to "0"
        )
      )
    )
  }

  Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
    Text("Analytics Events", style = MaterialTheme.typography.bodyMedium)
    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
      value = searchQuery,
      onValueChange = { searchQuery = it },
      placeholder = { Text("Search events...") },
      modifier = Modifier.fillMaxWidth(),
      leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
    )

    Spacer(modifier = Modifier.height(8.dp))

    LazyColumn {
      items(events) { event ->
        EventCard(
          event = event,
          isExpanded = events.indexOf(event) == expandedEventIndex,
          onToggleExpand = {
            expandedEventIndex = if (events.indexOf(event) == expandedEventIndex) null else events.indexOf(event)
          }
        )
        Spacer(modifier = Modifier.height(8.dp))
      }
    }
  }
}

@Composable
private fun EventCard(event: AnalyticsEvent, isExpanded: Boolean, onToggleExpand: () -> Unit) {
  Card(
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(event.name, style = MaterialTheme.typography.titleLarge)
          Text(event.timestamp, style = MaterialTheme.typography.labelMedium)
          SuggestionChip(
            onClick = { },
            label = { Text(event.key, color = Color.Blue) },
          )
        }
        IconButton(onClick = onToggleExpand) {
          Icon(
            if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = if (isExpanded) "Collapse" else "Expand"
          )
        }
      }

      if (isExpanded) {
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        Text("Provider: ${event.provider}", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Key-Value Map:", style = MaterialTheme.typography.titleMedium)
        event.keyValueMap.forEach { (key, value) ->
          Text("$key: $value", style = MaterialTheme.typography.bodyMedium)
        }
      }
    }
  }
}
