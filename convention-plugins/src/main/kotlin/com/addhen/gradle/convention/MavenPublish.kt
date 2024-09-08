// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.gradle.convention

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension

fun Project.configureMavenPublish() {
  with(pluginManager) {
    apply("maven-publish")
    apply("signing")

    publishing {
      // Configure all publications
      publications.withType<MavenPublication> {
        // Stub javadoc.jar artifact
        artifact(
          tasks.register("${name}JavadocJar", Jar::class) {
            archiveClassifier.set("javadoc")
            archiveAppendix.set(this@withType.name)
          },
        )

        // Provide artifacts information required by Maven Central
        pom {
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
            url.set("https://github.com/addhen/kanalytics")
          }
        }
      }
    }

    signing {
      if (project.hasProperty("signing.gnupg.keyName")) {
        useInMemoryPgpKeys(
          System.getenv("OSSRH_GPG_SECRET_KEY_ID"),
          System.getenv("OSSRH_GPG_SECRET_KEY"),
          System.getenv("OSSRH_GPG_SECRET_KEY_PASSWORD"),
        )
        sign(publishing.publications)
      }
    }
  }
}

private fun Project.publishing(action: PublishingExtension.() -> Unit) =
  extensions.configure<PublishingExtension>(action)

private fun Project.signing(action: SigningExtension.() -> Unit) =
  extensions.configure<SigningExtension>(action)

internal val Project.publishing: PublishingExtension
  get() = extensions.getByType<PublishingExtension>()
