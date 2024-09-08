// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.gradle.convention

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

fun Project.configureJava() {
  java {
    toolchain {
      // Build with JDK 21
      languageVersion.set(JavaLanguageVersion.of(21))
    }
  }

  // Set Java language version to 11
  tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = "11"
    targetCompatibility = "11"
  }
}

private fun Project.java(action: JavaPluginExtension.() -> Unit) {
  extensions.configure<JavaPluginExtension>(action)
}
