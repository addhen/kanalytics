// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("convention.plugin.android.library")
  id("convention.plugin.kotlin.multiplatform")
  id("convention.plugin.compose")
  alias(libs.plugins.kotlin.serialization)
}

android {
  namespace = "com.addhen.kanalytics.sample.shared"
}

kotlin {
  val isDebug = project.extra["kanalytics.sampleDebug"] == "debug"
  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.kanalytics)
        if (isDebug) {
          implementation(projects.kanalyticsViewer)
        } else {
          implementation(projects.kanalyticsViewerNoOp)
        }
        api(compose.material3)
        api(libs.touchlab.kermit)
        implementation(compose.runtime)
        implementation(compose.components.resources)
        implementation(libs.kotlinx.datetime)
        implementation(libs.lifecycle.viewmodel.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.kotlinx.serialization)
        implementation(libs.kotlinx.serialization)
      }
    }
  }
}
