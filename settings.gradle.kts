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
    maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/") }
    mavenLocal()
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
// https://docs.gradle.org/7.6/userguide/configuration_cache.html#config_cache:stable
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

// setting project name to kanalytics-lib to curtail an issue with conflicts kanalytics name due to
// gradle's typesafe project accessors feature
rootProject.name = "kanalytics-lib"

include(
  ":kanalytics",
  ":kanalytics-viewer",
  ":sample:android",
  ":sample:shared",
  ":sample:ios-framework",
)
include(":kanalytics-viewer-no-op")
