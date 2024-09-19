// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
public fun LocationScreen(
  locationViewModel: SampleViewModel,
  onClick: () -> Unit
) {
  // Showing how to consume location updates and last known location without compose [State]
  val uiState by locationViewModel.viewState.collectAsState()

  when (uiState.flag) {
    SampleViewModel.LocationUiState.Flag.LOADING -> FullScreenLoading()
    SampleViewModel.LocationUiState.Flag.ERROR -> Unit
    SampleViewModel.LocationUiState.Flag.IDLE -> {
      Samples(
        currentLocation = uiState.state,
        lastKnownLocation = uiState.state,
        onAnalytics = onClick,
      ) {
        locationViewModel.sendAnalyticsEvent()
      }
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
