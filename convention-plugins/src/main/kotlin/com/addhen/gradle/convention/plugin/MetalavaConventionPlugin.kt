// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.gradle.convention.plugin

import me.tylerbwong.gradle.metalava.extension.MetalavaExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MetalavaConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    with(pluginManager) {
      apply("me.tylerbwong.gradle.metalava")
    }

    metalava {
      filename.set("api/api.txt")
      sourcePaths.setFrom(
        target.kotlin.sourceSets
          .filterNot { it.name.contains("test", ignoreCase = true) }
          .flatMap { it.kotlin.sourceDirectories },
      )
    }

    tasks.named { it.startsWith("metalavaCheckCompatibility") }.configureEach {
      dependsOn(tasks.named { it.startsWith("generateResourceAccessors") })
    }
  }
}

private fun Project.metalava(action: MetalavaExtension.() -> Unit) =
  extensions.configure<MetalavaExtension>(action)

internal val Project.kotlin: KotlinMultiplatformExtension
  get() = extensions.getByType<KotlinMultiplatformExtension>()
