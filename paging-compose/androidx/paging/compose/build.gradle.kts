plugins {
  id("convention.plugin.android.library")
  id("convention.plugin.kotlin.multiplatform")
  id("convention.plugin.compose")
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        api(libs.paging.common)
        api(compose.runtime)
      }
    }
  }
}

android {
  namespace = "androidx.paging.compose"
}
