// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.gradle.convention.plugin

import com.addhen.gradle.convention.configureKotlin
import com.addhen.gradle.convention.configureSpotless
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    with(pluginManager) {
      apply("org.jetbrains.kotlin.multiplatform")
    }

    kotlin {
      applyDefaultHierarchyTemplate()
      if (pluginManager.hasPlugin("com.android.library")) {
        androidTarget {
          publishLibraryVariants("release")
        }
      }

      iosX64()
      iosArm64()
      iosSimulatorArm64()

      configureSpotless()
      configureKotlin()
      // Strict explicit mode
      explicitApi()
    }
  }
}

internal fun Project.kotlin(action: KotlinMultiplatformExtension.() -> Unit) {
  extensions.configure<KotlinMultiplatformExtension>(action)
}
