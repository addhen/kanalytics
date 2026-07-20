// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  alias(libs.plugins.androidKmpLibrary)
  id("convention.plugin.kotlin.multiplatform")
  alias(libs.plugins.dokka)
  id("convention.plugin.maven.publication")
  id("convention.plugin.metalava")
}

kotlin {
  android {
    namespace = "com.addhen.kanalytics"
    compileSdk = libs.versions.compileSdk.get().toInt()
    minSdk = libs.versions.minSdk.get().toInt()
  }

  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlinx.collections.immutable)
        implementation(libs.kotlinx.atomicfu)
      }
    }

    commonTest {
      dependencies {
        implementation(libs.kotlin.test)
      }
    }
  }
}

publishing {
  // Configure all publications
  publications.withType<MavenPublication> {

    // Provide artifacts information required by Maven Central
    pom {
      name.set("kanalytics")
      description.set(
        "A kotlin multiplatform library for sending analytics events to tracking platforms",
      )
    }
  }
}
