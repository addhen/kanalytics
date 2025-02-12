plugins {
  id("convention.plugin.android.library")
  id("convention.plugin.kotlin.android")
}

android {
  namespace = "com.addhen.kanalytics.android.common.test"
}

dependencies {
  //implementation(projects.kanalytics)
  implementation(projects.sample.android)
  api(libs.androidx.uiautomator)
}
