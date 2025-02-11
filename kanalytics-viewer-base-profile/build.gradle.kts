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

// This is the configuration block for the Baseline Profile plugin.
// You can specify to run the generators on a managed devices or connected devices.
baselineProfile {
  useConnectedDevices = true
}

dependencies {
  implementation(libs.androidx.junit)
  implementation(libs.androidx.espresso.core)
  implementation(libs.androidx.uiautomator)
  implementation(libs.androidx.benchmark.macro.junit4)
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
