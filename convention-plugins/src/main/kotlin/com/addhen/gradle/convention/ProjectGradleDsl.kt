// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.gradle.convention

import java.util.Optional
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType

val Project.libs
  get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.version(name: String): VersionConstraint = findVersion(name).get()

internal fun VersionCatalog.library(name: String): MinimalExternalModuleDependency {
  return findLibrary(name).get().get()
}

fun DependencyHandlerScope.implementation(
  artifact: Optional<Provider<MinimalExternalModuleDependency>>,
) {
  add("implementation", artifact.get())
}
