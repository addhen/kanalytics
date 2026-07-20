// Copyright 2025, Addhen Ltd and the kanalytics project contributors
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
    namespace = "com.addhen.kanalytics.viewer.noop"
    compileSdk = libs.versions.compileSdk.get().toInt()
    minSdk = libs.versions.minSdk.get().toInt()
  }

  sourceSets {
    commonMain {
      dependencies {
        api(projects.kanalytics)
        implementation(libs.kotlinx.datetime)
      }
    }
  }
}

// Configure test tasks to not fail when no tests are discovered
// This is appropriate for this no-op module that doesn't need tests
tasks.withType<Test> {
  failOnNoDiscoveredTests = false
}

publishing {
  // Configure all publications
  publications.withType<MavenPublication> {

    // Provide artifacts information required by Maven Central
    pom {
      name.set("kanalytics-viewer-no-op")
      description.set("A no-op kotlin multiplatform library for kanalytics viewer")
    }
  }
}
