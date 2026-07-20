// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  alias(libs.plugins.androidKmpLibrary)
  id("convention.plugin.kotlin.multiplatform")
  alias(libs.plugins.dokka)
}

dependencies {
  dokka(projects.kanalytics)
  dokka(projects.kanalyticsViewer)
  dokka(projects.kanalyticsViewerNoOp)
}

dokka {
  moduleName.set("KAnalytics")
}

kotlin {
  android {
    namespace = "com.addhen.kanalytics.viewer.docs"
    compileSdk = libs.versions.compileSdk.get().toInt()
    minSdk = libs.versions.minSdk.get().toInt()
  }
}
