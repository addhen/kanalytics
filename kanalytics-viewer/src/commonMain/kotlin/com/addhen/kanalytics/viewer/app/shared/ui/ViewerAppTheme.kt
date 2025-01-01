// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.data.prettyPrintJson
import com.addhen.kanalytics.viewer.app.shared.ui.component.EmptyContent
import com.addhen.kanalytics.viewer.app.shared.ui.theme.AppTheme
import com.addhen.kanalytics.viewer.app.shared.ui.theme.ColorContrast
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.material3.PaginatedDataTable
import com.seanproctor.datatable.paging.rememberPaginatedDataTableState

@Composable
public fun ViewerAppTheme(
  colorContrast: ColorContrast = ColorContrast.Default,
  content: @Composable () -> Unit,
) {
  AppTheme(colorContrast, content)
}

@Composable
public fun AnalyticsEventsScreen(lazyPagingItems: LazyPagingItems<EventData>) {
  var searchQuery by remember { mutableStateOf("") }

  Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
    if (lazyPagingItems.itemCount == 0 && lazyPagingItems.loadState.refresh != LoadState.Loading) {
      EmptyContent(
        title = { Text("No events found") },
        modifier = Modifier.fillMaxWidth(),
      )
    } else {
      Text("Analytics Events", style = MaterialTheme.typography.bodyMedium)
      Spacer(modifier = Modifier.height(8.dp))

      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Search events...") },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
      )

      Spacer(modifier = Modifier.height(16.dp))

      PaginatedDataTable(
        columns = listOf(
          DataColumn {
            Text("Timestamp")
          },
          DataColumn {
            Text("Event Name")
          },
          DataColumn {
            Text("Properties")
          },
        ),
        modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxWidth(),
        state = rememberPaginatedDataTableState(10),
      ) {

        for (rowIndex in 0 until lazyPagingItems.itemCount) {
          val event = lazyPagingItems[rowIndex]
          if (event != null) {
            row {
              onClick = { println("Row clicked: $rowIndex") }
              cell {
                Text(event.createdAt.toString())
              }
              cell {
                Text(event.name)
              }
              cell {
                Text(
                  event.properties.prettyPrintJson(),
                  maxLines = 2,
                  overflow = TextOverflow.Ellipsis,
                  )
              }
            }
          }
        }
      }
    }
  }
}
