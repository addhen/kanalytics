// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0


import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
  alias(libs.plugins.android.test)
  id("convention.plugin.kotlin.android")
  alias(libs.plugins.androidx.baselineprofile)
}

android {
  namespace = "com.addhen.kanalytics.viewer.base.profile"

  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    minSdk = 28

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  @Suppress("UnstableApiUsage")
  testOptions {
    managedDevices {
      devices {
        create<ManagedVirtualDevice>("api34") {
          device = "Pixel 6"
          apiLevel = 34
          systemImageSource = "aosp"
        }
      }
    }
  }

  targetProjectPath = ":sample:android"
}

dependencies {
  implementation(projects.androidCommonTest)
  implementation(libs.androidx.test.junit)
  implementation(libs.androidx.benchmark.macro)
  implementation(libs.androidx.espresso.core)
}

androidComponents {
  onVariants { v ->
    val artifactsLoader = v.artifacts.getBuiltArtifactsLoader()
    v.instrumentationRunnerArguments.put(
      "targetAppId",
      v.testedApks.map { artifactsLoader.load(it)?.applicationId },
    )
  }
}

@Suppress("UnstableApiUsage")
baselineProfile {
  managedDevices += "api34"
  useConnectedDevices = false
  enableEmulatorDisplay = true
}
