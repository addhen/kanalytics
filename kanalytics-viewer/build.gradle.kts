// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("convention.plugin.android.library")
  id("convention.plugin.kotlin.multiplatform")
  id("org.jetbrains.dokka")
  id("convention.plugin.maven.publication")
  id("convention.plugin.compose")
  id("convention.plugin.metalava")
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(project.projects.kanalytics)
        implementation(compose.material3)
        implementation(compose.runtime)
        implementation(compose.components.resources)
        implementation(libs.moko.permissions)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.collections.immutable)
        implementation(libs.kotlinx.datetime)
        implementation(libs.touchlab.kermit)
        implementation(libs.lifecycle.viewmodel.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.kotlinx.serialization)
        implementation(libs.moko.permissions)
        implementation(libs.moko.permissions.compose)
        implementation(libs.kotlinx.serialization)
      }
    }

    androidMain {
      dependencies {
        implementation(libs.androidx.core.ktx)
      }
    }

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

publishing {
  // Configure all publications
  publications.withType<MavenPublication> {

    // Provide artifacts information required by Maven Central
    pom {
      name.set("KAnalytics")
      description.set("A kotlin multiplatform library for getting a device's location")
    }
  }
}
