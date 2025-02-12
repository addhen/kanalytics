// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.base.profile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.addhen.kanalytics.android.common.test.AppTestScenarios
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {

  @get:Rule
  val rule = BaselineProfileRule()

  @Test
  fun generate() {
    // The application id for the running build variant is read from the instrumentation arguments.
    rule.collect(
      packageName = InstrumentationRegistry.getArguments().getString("targetAppId")
        ?: throw Exception("targetAppId not passed as instrumentation runner arg"),

      // See: https://d.android.com/topic/performance/baselineprofiles/dex-layout-optimizations
      includeInStartupProfile = true,
    ) {
      startActivityAndWait()
      device.allowNotifications(packageName)
      AppTestScenarios.allScenarios(device)
    }
  }
}
