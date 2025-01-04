// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.addhen.kanalytics.viewer.app.shared.data.repository.EventDataRepository

@Composable
public fun EventViewerScreen() {
  val viewModel = viewModel { EventViewerViewModel(EventDataRepository.Instance) }
  val uiState by viewModel.viewState.collectAsState()
  AnalyticsEventsScreen(uiState) {
    //viewModel.search(it)
  }
}
