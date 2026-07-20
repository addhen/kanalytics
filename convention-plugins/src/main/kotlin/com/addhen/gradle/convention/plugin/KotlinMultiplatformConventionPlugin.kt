// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.gradle.convention.plugin

import com.addhen.gradle.convention.configureKotlin
import com.addhen.gradle.convention.configureSpotless
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    with(pluginManager) {
      apply("org.jetbrains.kotlin.multiplatform")
    }

    kotlin {
      applyDefaultHierarchyTemplate()
      //iosX64()
      iosArm64()
      iosSimulatorArm64()

      targets.withType<KotlinNativeTarget>().configureEach {

        binaries.all {
          // Add linker flag for SQLite. See:
          // https://github.com/touchlab/SQLiter/issues/77
          linkerOpts("-lsqlite3")
        }

        compilations.configureEach {
          compileTaskProvider.configure {
            compilerOptions {
              // Various opt-ins
              freeCompilerArgs.addAll(
                "-opt-in=kotlinx.cinterop.ExperimentalForeignApi",
                "-opt-in=kotlinx.cinterop.BetaInteropApi",
                "-Xexpect-actual-classes",
              )
            }
          }
        }
      }

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
