// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("convention.plugin.android.library")
  id("convention.plugin.kotlin.android")
}

android {
  namespace = "com.addhen.kanalytics.android.common.test"
}

dependencies {
  // implementation(projects.kanalytics)
  implementation(projects.sample.android)
  api(libs.androidx.uiautomator)
}
