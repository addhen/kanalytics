// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.gradle.convention.plugin

import com.addhen.gradle.convention.configureSpotless
import com.addhen.gradle.convention.libs
import com.addhen.gradle.convention.version
import org.gradle.api.Plugin
import org.gradle.api.Project

class RootConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    val kanalyticsVersion = libs.version("kanalytics").requiredVersion

    allprojects {
      group = "com.addhen.kanalytics"
      version = kanalyticsVersion
    }
    configureSpotless()
  }
}
