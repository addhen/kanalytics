// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0


import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0
plugins {
  alias(libs.plugins.androidKmpLibrary)
  id("convention.plugin.kotlin.multiplatform")
  id("convention.plugin.compose")
}

kotlin {
  android {
    namespace = "com.addhen.kanaltyics.sample.iosframework"
    compileSdk = libs.versions.compileSdk.get().toInt()
    minSdk = libs.versions.minSdk.get().toInt()
  }

  val isDebug = project.extra["kanalytics.sampleDebug"] == "debug"
  sourceSets {
    commonMain {
      dependencies {
        if (isDebug) {
          api(projects.kanalyticsViewer)
        } else {
          implementation(projects.kanalyticsViewerNoOp)
        }
        implementation(projects.sample.shared)
      }
    }

    targets.withType<KotlinNativeTarget>().configureEach {
      binaries.framework {
        isStatic = true
        baseName = "KAnalyticsViewerKt"
        export(projects.kanalyticsViewer)
      }
    }
  }
}
