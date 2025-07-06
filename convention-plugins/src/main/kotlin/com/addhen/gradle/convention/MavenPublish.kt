// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.gradle.convention

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureMavenPublish() {
  with(pluginManager) {
    apply("com.vanniktech.maven.publish")
    val kanalyticsVersion = libs.version("kanalytics").requiredVersion

    mavenPublishing {
      // `true` will automatically publish the artifact to Maven Central Portal.
      publishToMavenCentral(true)
      signAllPublications()
      coordinates(
        groupId = "com.addhen.kanalytics",
        artifactId = project.name,
        version = kanalyticsVersion
      )

      pom {
        name.set(project.name)
        description.set("Kotlin Multiplatform Analytics with a debug viewer")
        url.set("https://github.com/addhen/kanalytics")

        licenses {
          license {
            name.set("Apache 2.0")
            url.set("https://opensource.org/license/apache-2-0")
          }
        }

        developers {
          developer {
            id.set("eyedol")
            name.set("Henry Addo")
            organization.set("Addhen Ltd")
            organizationUrl.set("https://www.addhen.com")
          }
        }
        scm {
          connection.set("scm:git:git@github.com:addhen/kanalytics.git")
          developerConnection.set("scm:git:ssh://git@github.com:addhen/kanalytics.git")
          url.set("https://github.com/addhen/kanalytics")
        }
      }
    }
  }
}

private fun Project.mavenPublishing(action: MavenPublishBaseExtension.() -> Unit) =
  extensions.configure<MavenPublishBaseExtension>(action)
