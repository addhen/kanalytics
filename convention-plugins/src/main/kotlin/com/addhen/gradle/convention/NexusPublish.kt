// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.gradle.convention

import io.github.gradlenexus.publishplugin.NexusPublishExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureNexusPublish() {
  with(pluginManager) { apply("io.github.gradle-nexus.publish-plugin") }
  val kanalyticsVersion = libs.version("kanalytics").requiredVersion
  allprojects {
    group = "com.addhen.kanalytics"
    version = kanalyticsVersion
  }

  nexusPublishing {
    // Configure maven central repository
    // https://github.com/gradle-nexus/publish-plugin#publishing-to-maven-central-via-sonatype-ossrh
    repositories {
      sonatype {
        username.set(System.getenv("OSSRH_USERNAME"))
        password.set(System.getenv("OSSRH_PASSWORD"))
        nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
        snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
      }
    }
  }
}

private fun Project.nexusPublishing(action: NexusPublishExtension.() -> Unit) =
  extensions.configure<NexusPublishExtension>(action)
