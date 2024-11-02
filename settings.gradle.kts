// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0


pluginManagement {
  includeBuild("convention-plugins")
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
    mavenLocal()
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
// https://docs.gradle.org/7.6/userguide/configuration_cache.html#config_cache:stable
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

// setting project name to klocation-lib to curtail an issue with conflicts klocation name due to
// gradle's typesafe project accessors feature
rootProject.name = "kanalytics-lib"

include(
  ":paging-compose:androidx:paging:compose",
  ":kanalytics",
  ":kanalytics-viewer",
  ":sample:android",
  ":sample:shared",
  ":sample:ios-framework",
)
