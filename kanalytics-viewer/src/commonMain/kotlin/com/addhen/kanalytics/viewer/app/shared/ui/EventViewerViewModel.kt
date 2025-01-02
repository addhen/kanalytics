// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.data.repository.EventDataRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart

@OptIn(ExperimentalCoroutinesApi::class)
internal class EventViewerViewModel(
  private val eventRepository: EventDataRepository,
) : ViewModel() {

  private val uiAction = MutableSharedFlow<UiAction>()
  val eventPagingData: Flow<PagingData<EventData>>

  init {
    eventPagingData = uiAction
      .filterIsInstance<UiAction.LoadEvents>()
      .onStart { emit(UiAction.LoadEvents) }
      .flatMapLatest { loadEvents() }
      .distinctUntilChanged()
      .cachedIn(viewModelScope)
  }

  private fun loadEvents(pagingConfig: PagingConfig = PAGING_CONFIG): Flow<PagingData<EventData>> =
    eventRepository.getAll(pagingConfig)

  sealed interface UiAction {
    data object LoadEvents : UiAction
  }

  companion object {
    private val PAGING_CONFIG = PagingConfig(
      pageSize = 10,
      initialLoadSize = 32,
    )
  }
}
