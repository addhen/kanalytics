// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
public fun EventViewerScreen() {
  val viewModel: EventViewerViewModel = viewModel(factory = EventViewerViewModel.Factory)
  val uiState by viewModel.viewState.collectAsStateWithLifecycle()

  AnalyticsEventsScreen(
    uiState,
    snackbarHostState = SnackbarHostState(),
    uiMessageStateHolder = viewModel.uiMessageStateHolder,
    onDeleteAllEvents = { viewModel.action(EventViewerViewModel.UiAction.DeleteAllEvents) },
  ) { searchQuery ->
    if (searchQuery.isNotBlank()) {
      viewModel.action(EventViewerViewModel.UiAction.SearchEvents(searchQuery))
    } else {
      viewModel.action(EventViewerViewModel.UiAction.LoadEvents)
    }
  }
}
