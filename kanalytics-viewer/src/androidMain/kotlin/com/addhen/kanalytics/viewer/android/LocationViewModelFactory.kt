// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.addhen.kanalytics.viewer.app.shared.ui.EventViewerViewModel

@Suppress("UNCHECKED_CAST")
internal class LocationViewModelFactory() : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return EventViewerViewModel() as T
  }
}
