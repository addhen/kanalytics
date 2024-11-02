// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.ui.component.EmptyContent
import com.addhen.kanalytics.viewer.app.shared.ui.theme.AppTheme
import com.addhen.kanalytics.viewer.app.shared.ui.theme.ColorContrast

@Composable
public fun ViewerAppTheme(
  colorContrast: ColorContrast = ColorContrast.Default,
  content: @Composable () -> Unit
) {
  AppTheme(colorContrast, content)
}

@Composable
public fun AnalyticsEventsScreen(
  lazyPagingItems: LazyPagingItems<EventData>
) {
  var expandedEventIndex by remember { mutableStateOf<Int?>(null) }
  var searchQuery by remember { mutableStateOf("") }

  Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
    if (lazyPagingItems.itemCount == 0 && lazyPagingItems.loadState.refresh != LoadState.Loading) {
        EmptyContent(
          title = { Text("No events found") },
          modifier = Modifier.fillMaxWidth()
        )
    } else {
    Text("Analytics Events", style = MaterialTheme.typography.bodyMedium)
    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
      value = searchQuery,
      onValueChange = { searchQuery = it },
      placeholder = { Text("Search events...") },
      modifier = Modifier.fillMaxWidth(),
      leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
    )

    Spacer(modifier = Modifier.height(16.dp))

    LazyColumn {

      if (lazyPagingItems.itemCount == 0 && lazyPagingItems.loadState.refresh != LoadState.Loading) {
        item {
          EmptyContent(
            title = { Text("No events found") },
            modifier = Modifier
              .fillMaxSize()
              .padding(vertical = 64.dp),
          )
        }
      }

      items(
        count = lazyPagingItems.itemCount,
        key = lazyPagingItems.itemKey { it.id},
      ) { index ->
        val event = lazyPagingItems[index]
        if (event != null) {
          EventCard(
            event = event,
            isExpanded = index == expandedEventIndex,
            onToggleExpand = {
              expandedEventIndex =
                if (index == expandedEventIndex) null else index
            }
          )
          Spacer(modifier = Modifier.height(8.dp))
        }
      }
    }
    }
  }
}

@Composable
private fun EventCard(
  event: EventData,
  isExpanded: Boolean,
  onToggleExpand: () -> Unit
) {
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
          Text(event.name, style = MaterialTheme.typography.titleMedium)
          Text(event.createdAt.toString(), style = MaterialTheme.typography.labelMedium)
          Spacer(modifier = Modifier.height(8.dp))
          SuggestionChip(
            onClick = { },
            label = { Text(event.provider) },
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
        event.properties.forEach { (key, value) ->
          KeyValueView(
            key = key,
            value = value.toString(),
            textStyle = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace),
            modifier = Modifier.fillMaxWidth()
          )
        }
      }
    }
  }
}


@Composable
private fun KeyValueView(
  key: String,
  value: String?,
  textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
  modifier: Modifier = Modifier,
) {
  Row(modifier = modifier) {
    Text(
      text = "$key:",
      style = textStyle.copy(fontWeight = FontWeight.Bold),
      modifier = Modifier.widthIn(min = 60.dp).weight(3f)
    )
    Spacer(Modifier.width(2.dp))
    Text(
      text = value ?: "",
      style = textStyle,
      modifier = Modifier.weight(2f)
    )
  }
}
