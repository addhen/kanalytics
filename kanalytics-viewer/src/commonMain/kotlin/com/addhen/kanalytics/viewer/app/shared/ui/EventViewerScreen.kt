// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.data.repository.EventDataRepository

@Composable
public fun EventViewerScreen() {
  val viewModel = viewModel { EventViewerViewModel(EventDataRepository.Instance) }
  // Showing how to consume location updates and last known location without compose [State]
  val lazyPagingItems: LazyPagingItems<EventData> = viewModel.eventPagingData.collectAsLazyPagingItems()
  AnalyticsEventsScreen(lazyPagingItems = lazyPagingItems)
}
