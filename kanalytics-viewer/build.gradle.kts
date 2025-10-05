// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("convention.plugin.android.library")
  id("convention.plugin.kotlin.multiplatform")
  alias(libs.plugins.dokka)
  id("convention.plugin.maven.publication")
  id("convention.plugin.compose")
  id("convention.plugin.metalava")
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.sqldelight)
  alias(libs.plugins.mokkery)
  alias(libs.plugins.androidx.baselineprofile)
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
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.collections.immutable)
        implementation(libs.kotlinx.datetime)
        implementation(libs.touchlab.kermit)
        implementation(libs.lifecycle.viewmodel.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.kotlinx.serialization)
        implementation(libs.sqldelight.coroutines)
        implementation(libs.sqldelight.primitive)
        implementation(libs.data.table.material)
        implementation(libs.json.tree)
        implementation(libs.multiplatformsettings.core)
        implementation(libs.multiplatformsettings.coroutines)
      }
    }

    androidMain {
      dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.startup.runtime)
        implementation(libs.androidx.preference)
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
        implementation(libs.coroutines.test)
      }
    }
  }
}

android {
  namespace = "com.addhen.kanalytics.viewer"
  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }

  dependencies {
    baselineProfile(projects.kanalyticsViewerBaseProfile)
  }

  baselineProfile {
    mergeIntoMain = true
    saveInSrc = true
    filter {
      include("com.addhen.kanalytics.viewer.**")
    }
  }
}

sqldelight {
  databases {
    create("EventViewerDatabase") {
      packageName = "com.addhen.kanalytics.viewer.app.shared.data.database.sqlidelight"
    }
  }
}

publishing {
  // Configure all publications
  publications.withType<MavenPublication> {

    // Provide artifacts information required by Maven Central
    pom {
      name.set("kanalytics-viewer")
      description.set("A kotlin multiplatform library for viewing kanalytics events")
    }
  }
}
