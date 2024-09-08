// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.gradle.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

fun Project.configureKotlin() {
  // Configure Java to use our chosen language level. Kotlin will automatically pick this up
  configureJava()

  tasks.withType<KotlinCompilationTask<*>>().configureEach {
    compilerOptions {
      allWarningsAsErrors.set(true)
      freeCompilerArgs.add(
        // expect/actual classes (including interfaces, objects, annotations, enums, actual typealiases) in Beta
        // https://youtrack.jetbrains.com/issue/KT-61573
        "-Xexpect-actual-classes"
      )
      if (this is KotlinJvmCompilerOptions) {
        // Target JVM 11 bytecode
        jvmTarget.set(JvmTarget.JVM_11)
        freeCompilerArgs.addAll(
          "-Xjsr305=strict",
          // Match JVM assertion behavior:
          // https://publicobject.com/2019/11/18/kotlins-assert-is-not-like-javas-assert/
          "-Xassertions=jvm",
          // Potentially useful for static analysis tools or annotation processors.
          "-Xemit-jvm-type-annotations",
          // Enable new jvm-default behavior
          // https://blog.jetbrains.com/kotlin/2020/07/kotlin-1-4-m3-generating-default-methods-in-interfaces/
          "-Xjvm-default=all",
          // https://kotlinlang.org/docs/whatsnew1520.html#support-for-jspecify-nullness-annotations
          "-Xtype-enhancement-improvements-strict-mode",
          "-Xjspecify-annotations=strict",
        )
      }
    }
  }
}
