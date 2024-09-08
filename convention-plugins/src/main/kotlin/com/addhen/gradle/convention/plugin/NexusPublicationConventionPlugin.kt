// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.gradle.convention.plugin

import com.addhen.gradle.convention.configureNexusPublish
import org.gradle.api.Plugin
import org.gradle.api.Project

class NexusPublicationConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    configureNexusPublish()
  }
}
