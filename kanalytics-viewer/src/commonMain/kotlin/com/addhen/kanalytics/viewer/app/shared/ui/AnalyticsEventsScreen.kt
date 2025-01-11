// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.Res
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.confirm_delete_all_events_message
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.confirm_delete_all_events_title
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.delete_all_events_content_description
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.event_name
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.loading_events
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.no_events_found
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.properties
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.retry
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.timestamp
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.viewer_app_name
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.data.toJsonString
import com.addhen.kanalytics.viewer.app.shared.ui.component.ConfirmationDialog
import com.addhen.kanalytics.viewer.app.shared.ui.component.EmptyContent
import com.addhen.kanalytics.viewer.app.shared.ui.component.LoadingText
import com.addhen.kanalytics.viewer.app.shared.ui.component.SearchTextFieldAppBar
import com.addhen.kanalytics.viewer.app.shared.ui.component.SnackbarMessageEffect
import com.addhen.kanalytics.viewer.app.shared.ui.component.UiMessageStateHolder
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.material3.PaginatedDataTable
import com.seanproctor.datatable.paging.rememberPaginatedDataTableState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.stringResource

internal const val SEARCH_SCREEN_TEST_TAG = "SearchScreenTestTag"
internal const val DELETE_ALL_EVENTS_TEST_TAG = "DeleteAllEventsTestTag"

@Composable
internal fun AnalyticsEventsScreen(
  uiState: EventViewerViewModel.EventViewerUiState,
  snackbarHostState: SnackbarHostState,
  uiMessageStateHolder: UiMessageStateHolder,
  onNavigateToDetail: (Long, String) -> Unit,
  onDeleteAllEvents: () -> Unit,
  onSearchQueryChanged: (String) -> Unit,
) {
  var showDialog by remember { mutableStateOf(false) }

  if (showDialog) {
    ConfirmDeleteDialog(
      onConfirm = {
        showDialog = false
        onDeleteAllEvents()
      },
      onDismiss = { showDialog = false }
    )
  }

  val retryMessageText = stringResource(Res.string.retry)
  SnackbarMessageEffect(
    snackbarHostState = snackbarHostState,
    actionLabel = retryMessageText,
    uiMessageStateHolder = uiMessageStateHolder,
  )

  ViewerAppScaffold(
    title = stringResource(Res.string.viewer_app_name),
    searchTopBar = {
      SearchTextFieldAppBar(
        searchQuery = uiState.searchQuery,
        onSearchQueryChanged = onSearchQueryChanged,
        testTag = SEARCH_SCREEN_TEST_TAG,
      )
    },
    actions = {
      IconButton(
        modifier = Modifier.testTag(DELETE_ALL_EVENTS_TEST_TAG),
        onClick = { showDialog = true },
      ) {
        Icon(
          imageVector = Icons.Default.Delete,
          contentDescription = stringResource(Res.string.delete_all_events_content_description) ,
        )
      }
    },
    snackbarHostState = snackbarHostState,
  ) { AnalyticsEventsContent(uiState, onNavigateToDetail) }
}

@Composable
private fun AnalyticsEventsContent(
  uiState: EventViewerViewModel.EventViewerUiState,
  onNavigateToDetail: (Long, String) -> Unit
) {
  Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
    AnimatedVisibility(
      modifier = Modifier.fillMaxSize(),
      visible = (uiState.flag == EventViewerViewModel.EventViewerUiState.Flag.LOADING),
      enter = fadeIn(),
      exit = fadeOut(),
    ) {
      LoadingText(
        modifier = Modifier
          .fillMaxSize()
          .background(MaterialTheme.colorScheme.background),
      )
    }
    when (uiState.flag) {
      EventViewerViewModel.EventViewerUiState.Flag.LOADING -> {
        // do nothing as the loading state is handled by the AnimatedVisibility
      }

      EventViewerViewModel.EventViewerUiState.Flag.IDLE -> {
        if (uiState.events.isEmpty()) {
          EmptyContent(
            title = { Text(stringResource(Res.string.no_events_found)) },
            modifier = Modifier.fillMaxWidth(),
          )
        } else {
          PaginatedDataTableContent(
            uiState.events.toImmutableList(),
            uiState.searchQuery,
            onNavigateToDetail
          )
        }
      }
    }
  }
}

@Composable
private fun PaginatedDataTableContent(
  events: ImmutableList<EventData>,
  searchText: String,
  onNavigateToDetail: (Long, String) -> Unit
) {
  PaginatedDataTable(
    columns = listOf(
      DataColumn {
        Text(stringResource(Res.string.timestamp))
      },
      DataColumn {
        Text(stringResource(Res.string.event_name))
      },
      DataColumn {
        Text(stringResource(Res.string.properties))
      },
    ),
    contentPadding = PaddingValues(horizontal = 8.dp),
    modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxWidth(),
    state = rememberPaginatedDataTableState(10),
  ) {
    for (rowIndex in 0 until events.size) {
      val event = events[rowIndex]
      row {
        onClick = {
          onNavigateToDetail(event.id ?: 0, event.name)
        }
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

@Composable
private fun ConfirmDeleteDialog(
  onConfirm: () -> Unit,
  onDismiss: () -> Unit
) {
  ConfirmationDialog(
    title = stringResource(Res.string.confirm_delete_all_events_title),
    message = stringResource(Res.string.confirm_delete_all_events_message),
    onConfirm = onConfirm,
    onDismiss = onDismiss,
  )
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
