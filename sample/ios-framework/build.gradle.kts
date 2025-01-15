// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0
plugins {
  id("convention.plugin.android.library")
  id("convention.plugin.kotlin.multiplatform")
  id("convention.plugin.compose")
}

android {
  namespace = "com.addhen.kanaltyics.sample.iosframework"
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.kanalyticsViewer)
        implementation(projects.sample.shared)
      }
    }

    targets.withType<KotlinNativeTarget>().configureEach {
      binaries.framework {
        isStatic = true
        baseName = "KAnalyticsKt"
      }
    }
  }
}
