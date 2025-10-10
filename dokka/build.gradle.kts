// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("convention.plugin.android.library")
  id("convention.plugin.kotlin.multiplatform")
  alias(libs.plugins.dokka)
}

dependencies {
  dokka(projects.kanalytics)
  dokka(projects.kanalyticsViewer)
  dokka(projects.kanalyticsViewerNoOp)
}

android {
  namespace = "com.addhen.kanalytics.viewer.docs"
}

dokka {
  moduleName.set("KAnalytics")
}
