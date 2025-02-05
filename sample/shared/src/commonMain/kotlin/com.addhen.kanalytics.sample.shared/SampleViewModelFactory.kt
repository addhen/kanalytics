// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.addhen.kanalytics.KAnalytics
import com.addhen.kanalytics.sample.shared.trackers.FirebaseTracker
import com.addhen.kanalytics.viewer.KAnalyticsCollector
import com.addhen.kanalytics.viewer.KAnalyticsInterceptor
import com.addhen.kanalytics.viewer.RetentionPolicyManager
import kotlin.reflect.KClass

internal fun createKAnalytics(shouldShowNotification: Boolean, numberOfDays: Int): KAnalytics {
  val collector = KAnalyticsCollector(
    showNotification = shouldShowNotification,
    duration = RetentionPolicyManager.DayDuration(numberOfDays),
  )
  return KAnalytics.Builder()
    .addTracker(FirebaseTracker())
    .addInterceptor(KAnalyticsInterceptor(collector))
    .build()
}

public val viewModel: SampleViewModel = SampleViewModelFactory(
  kAnalytics = createKAnalytics(shouldShowNotification = true, numberOfDays = 7),
).create(SampleViewModel::class, CreationExtras.Empty)

@Suppress("UNCHECKED_CAST")
public class SampleViewModelFactory(private val kAnalytics: KAnalytics) :
  ViewModelProvider.Factory {

  public override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T =
    SampleViewModel(kAnalytics) as T
}
