// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.android.common.test

import android.os.SystemClock
import android.util.Log
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.SearchCondition
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

object AppTestScenarios {

  private const val TAG = "AppTestScenarios"

  fun allScenarios(device: UiDevice) {
    Log.i(TAG, "Starting all scenarios")
    device.waitForIdle()

    device.testSampleMainActivity() || return
    device.triggerEvent()
    device.launchViewerApp()
    device.navigateToEventDetail()
  }

  fun UiDevice.testSampleMainActivity(): Boolean {
    waitForIdle()
    return true
  }

  fun UiDevice.triggerEvent() {
    waitForIdle()
    runAction(By.res("trigger_analytics_event_test_tag")) { click() }
    waitForIdle()
  }

  fun UiDevice.launchViewerApp() {
    waitForIdle()
    runAction(By.res("event_viewer_test_tag")) { click() }
    waitForIdle()
  }

  fun UiDevice.navigateToEventDetail() {
    Log.i(TAG, "Navigating to Event Detail Screen")
    waitForIdle()
    runAction(By.res("event_item_test_tag")) { click() }
    waitForIdle()
  }

  private fun UiDevice.runAction(
    selector: BySelector,
    maxRetries: Int = 6,
    action: UiObject2.() -> Unit,
  ) {
    waitForObject(selector)

    retry(maxRetries = maxRetries, delay = 1.seconds) {
      // Wait for idle, to avoid recompositions causing StaleObjectExceptions
      waitForIdle()

      requireNotNull(findObject(selector)).action()
    }
  }

  fun UiDevice.waitForObject(selector: BySelector, timeout: Duration = 5.seconds): UiObject2 {
    if (wait(Until.hasObject(selector), timeout)) {
      return findObject(selector)
    }
    error("Object with selector [$selector] not found")
  }

  fun <R> UiDevice.wait(condition: SearchCondition<R>, timeout: Duration): R =
    wait(condition, timeout.inWholeMilliseconds)

  private fun retry(maxRetries: Int, delay: Duration, block: () -> Unit) {
    repeat(maxRetries) { run ->
      val result = runCatching { block() }
      if (result.isSuccess) {
        return
      }
      if (run == maxRetries - 1) {
        result.getOrThrow()
      } else {
        SystemClock.sleep(delay.inWholeMilliseconds)
      }
    }
  }
}
