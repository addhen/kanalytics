// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.base.profile

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.addhen.kanalytics.android.common.test.AppTestScenarios
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
//@LargeTest
class StartupBenchmarks {

  @get:Rule
  val rule = MacrobenchmarkRule()

  @Test
  fun startup() = benchmark(CompilationMode.None())

  private fun benchmark(compilationMode: CompilationMode) {
    // The application id for the running build variant is read from the instrumentation arguments.
    rule.measureRepeated(
      packageName = InstrumentationRegistry.getArguments().getString("targetAppId")
        ?: throw Exception("targetAppId not passed as instrumentation runner arg"),
      metrics = listOf(StartupTimingMetric()),
      compilationMode = compilationMode,
      startupMode = StartupMode.COLD,
      iterations = 10,
      setupBlock = {
        device.allowNotifications(packageName)
      },
      measureBlock = {
        pressHome()
        startActivityAndWait()
        AppTestScenarios.viewerAppStartUp(device)
      },
    )
  }
}
