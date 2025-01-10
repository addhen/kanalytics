// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
public fun SampleScreen(sampleViewModel: SampleViewModel, onEventViewerTrigger: () -> Unit) {
  val uiState by sampleViewModel.viewState.collectAsStateWithLifecycle()

  when (uiState.flag) {
    SampleViewModel.LocationUiState.Flag.LOADING -> FullScreenLoading()
    SampleViewModel.LocationUiState.Flag.IDLE -> {
      Samples(onTriggerAnalytics = {sampleViewModel.sendAnalyticsEvent()}, onEventViewerTrigger)
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
