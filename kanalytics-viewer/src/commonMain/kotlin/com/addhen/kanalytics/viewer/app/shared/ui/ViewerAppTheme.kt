// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.addhen.kanalytics.viewer.app.shared.data.toJsonString
import com.addhen.kanalytics.viewer.app.shared.ui.component.EmptyContent
import com.addhen.kanalytics.viewer.app.shared.ui.component.SearchTextFieldAppBar
import com.addhen.kanalytics.viewer.app.shared.ui.theme.AppTheme
import com.addhen.kanalytics.viewer.app.shared.ui.theme.ColorContrast
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.material3.PaginatedDataTable
import com.seanproctor.datatable.paging.rememberPaginatedDataTableState

internal const val SEARCH_SCREEN_TEST_TAG = "SearchScreenTestTag"

@Composable
public fun ViewerAppTheme(
  colorContrast: ColorContrast = ColorContrast.Default,
  content: @Composable () -> Unit,
) {
  AppTheme(colorContrast, content)
}

@Composable
internal fun AnalyticsEventsScreen(
  uiState: EventViewerViewModel.EventViewerUiState,
  onSearchQueryChanged: (String) -> Unit = {},
) {
  val searchQuery by remember { mutableStateOf("state") }

  ViewerAppScaffold(
    title = "",
    topBar = {
      SearchTextFieldAppBar(
        searchQuery = searchQuery,
        onSearchQueryChanged = onSearchQueryChanged,
        testTag = SEARCH_SCREEN_TEST_TAG,
      )
    },
  ) { AnalyticsEventsContent(uiState, searchQuery) }
}

@Composable
private fun AnalyticsEventsContent(
  uiState: EventViewerViewModel.EventViewerUiState,
  searchText: String,
) {
  Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
    when (uiState.flag) {
      EventViewerViewModel.EventViewerUiState.Flag.LOADING -> {
        Text("Loading events...")
      }
      EventViewerViewModel.EventViewerUiState.Flag.IDLE -> {
        if (uiState.events.isEmpty()) {
          EmptyContent(
            title = { Text("No events found") },
            modifier = Modifier.fillMaxWidth(),
          )
        } else {
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
            contentPadding = PaddingValues(horizontal = 8.dp),
            modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxWidth(),
            state = rememberPaginatedDataTableState(10),
          ) {
            for (rowIndex in 0 until uiState.events.size) {
              val event = uiState.events[rowIndex]
              row {
                onClick = { println("Row clicked: $rowIndex") }
                isHeader = true
                cell {
                  Text(event.createdAt.toString())
                }
                cell {
                  Text(searchText.highlightText(event.name))
                }
                cell {
                  Text(
                    searchText.highlightText(event.properties.toJsonString()),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                  )
                }
              }
            }
          }
        }
      }
      EventViewerViewModel.EventViewerUiState.Flag.ERROR -> TODO()
    }
  }
}

@Composable
private fun String.highlightText(text: String): AnnotatedString {
  return buildAnnotatedString {
    val highlightRange = text.getMatchIndexRange(this@highlightText)
    append(text.take(highlightRange.first))
    withStyle(
      SpanStyle(
        background = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
        textDecoration = TextDecoration.Underline,
      ),
    ) {
      append(text.substring(highlightRange))
    }
    append(text.takeLast(kotlin.math.max((text.lastIndex - highlightRange.last), 0)))
  }
}

private fun String.getMatchIndexRange(queryText: String = ""): IntRange {
  if (queryText.isBlank()) return IntRange.EMPTY

  val startIndex = this.indexOf(queryText, ignoreCase = true)
  return if (startIndex >= 0) {
    startIndex until (startIndex + queryText.length)
  } else {
    IntRange.EMPTY
  }
}
