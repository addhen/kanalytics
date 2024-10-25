// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
public fun EventViewerScreen() {
  val viewModel = viewModel { EventViewerViewModel() }
  // Showing how to consume location updates and last known location without compose [State]
  val uiState by viewModel.viewState.collectAsState()

  when (uiState.flag) {
    EventViewerViewModel.LocationUiState.Flag.LOADING -> FullScreenLoading()
    EventViewerViewModel.LocationUiState.Flag.ERROR -> Unit
    EventViewerViewModel.LocationUiState.Flag.IDLE -> {
      AnalyticsEventsScreen()
    }
  }
}

@Composable
private fun FullScreenLoading() {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .wrapContentSize(Alignment.Center),
  ) {
    CircularProgressIndicator()
  }
}
