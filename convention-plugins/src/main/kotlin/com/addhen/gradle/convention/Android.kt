// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.gradle.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.HasUnitTestBuilder
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureAndroidApplication() {
  val localCompileSdk = libs.version("compileSdk").requiredVersion.toInt()
  val localMinSdk = libs.version("minSdk").requiredVersion.toInt()
  val localTargetSdk = libs.version("targetSdk").requiredVersion.toInt()

  extensions.configure<ApplicationExtension> {
    compileSdk = localCompileSdk

    defaultConfig {
      minSdk = localMinSdk
      targetSdk = localTargetSdk

      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_11
      targetCompatibility = JavaVersion.VERSION_11
    }
  }

  configureAndroidComponents()
}

fun Project.configureAndroidLibrary() {
  val localCompileSdk = libs.version("compileSdk").requiredVersion.toInt()
  val localMinSdk = libs.version("minSdk").requiredVersion.toInt()

  extensions.configure<LibraryExtension> {
    compileSdk = localCompileSdk

    defaultConfig {
      minSdk = localMinSdk

      testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_11
      targetCompatibility = JavaVersion.VERSION_11
    }
  }

  configureAndroidComponents()
}

private fun Project.configureAndroidComponents() {
  extensions.configure(AndroidComponentsExtension::class.java) {
    beforeVariants(selector().withBuildType("release")) { variantBuilder ->
      (variantBuilder as? HasUnitTestBuilder)?.apply {
        enableUnitTest = false
      }
    }
  }
}
