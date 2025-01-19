// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.addhen.kanalytics.KAnalytics
import com.addhen.kanalytics.KAnalyticsInterceptor
import com.addhen.kanalytics.sample.shared.trackers.FirebaseTracker
import kotlin.reflect.KClass

public val kanalytics: KAnalytics = KAnalytics.Builder()
  .addTracker(FirebaseTracker())
  .addInterceptor(KAnalyticsInterceptor())
  .build()

public val viewModel: SampleViewModel = SampleViewModelFactory(
  kanalytics = kanalytics,
).create(SampleViewModel::class, CreationExtras.Empty)

@Suppress("UNCHECKED_CAST")
public class SampleViewModelFactory(
  private val kanalytics: KAnalytics,
) : ViewModelProvider.Factory {

  public override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
    return SampleViewModel(kanalytics) as T
  }
}
