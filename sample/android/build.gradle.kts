// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("convention.plugin.android.application")
  id("convention.plugin.kotlin.android")
  id("convention.plugin.compose")
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "com.addhen.kanalytics.sample.android"

  defaultConfig {
    versionCode = 1
    versionName = "1.0"
    applicationId = "com.addhen.kanalytics.sample.android"
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      isShrinkResources = true
      signingConfig = signingConfigs["debug"]
      proguardFiles(
        "proguard-rules.pro",
        getDefaultProguardFile("proguard-android-optimize.txt"),
      )
    }
  }

  packaging {
    resources.excludes += setOf(
      // Exclude AndroidX version files
      "META-INF/*.version",
      // Exclude consumer proguard files
      "META-INF/proguard/*",
      // Exclude the Firebase/Fabric/other random properties files
      "/*.properties",
      "fabric/*.properties",
      "META-INF/*.properties",
      // License files
      "LICENSE*",
      // Exclude Kotlin unused files
      "META-INF/**/previous-compilation-data.bin",
    )
  }
}

dependencies {
  implementation(projects.kanalyticsViewer)
  implementation(projects.sample.shared)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(libs.material)
  implementation(libs.androidx.appcompat)
  implementation(libs.accompanist.permissions)
  implementation(libs.androidx.navigation.compose)
}
