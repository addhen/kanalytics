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
  alias(libs.plugins.sqldelight)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(projects.kanalytics)
        implementation(compose.material3)
        implementation(compose.runtime)
        implementation(compose.components.resources)
        implementation(compose.materialIconsExtended)
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
        implementation(libs.sqldelight.coroutines)
        implementation(libs.sqldelight.primitive)
      }
    }

    androidMain {
      dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.sqldelight.android)
      }
    }

    iosMain {
      dependencies {
        implementation(libs.sqldelight.native)
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
  namespace = "com.addhen.kanalytics.viewer.app.shared"
  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }
}

sqldelight {
  databases {
    create("EventViewer") {
      packageName = "com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight"
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
