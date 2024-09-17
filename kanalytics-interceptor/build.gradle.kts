// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("convention.plugin.android.library")
  id("convention.plugin.kotlin.multiplatform")
  id("org.jetbrains.dokka")
  id("convention.plugin.metalava")
}

kotlin {
  sourceSets {
    commonTest {
      dependencies {
        implementation(libs.kotlin.test)
      }
    }
  }
}

android {
  namespace = "com.addhen.kanalytics.interceptor"
  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}
