// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.addhen.kanalytics.KAnalytics
import com.addhen.kanalytics.sample.shared.SampleViewModel

@Suppress("UNCHECKED_CAST")
class LocationViewModelFactory(
  val kanalytics: KAnalytics
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return SampleViewModel(kanalytics) as T
  }
}
