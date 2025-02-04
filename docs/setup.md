Setup KAnalytics
===============

KAnalytics comes with three artifacts: `kanalytics`, `kanalytics-viewer`, and `kanalytics-viewer-no-op`.
The `kanalytics` artifact is the main library for collecting analytics, while the `kanalytics-viewer`
artifact is a companion app that helps developers and QA teams manage analytics events in their applications.
The `kanalytics-viewer-no-op` artifact is a **no-op** version of the viewer, which can be used in
production environments where you don't want to include the viewer.

## Installation

Add `kanalytics` artifact in your project to be able to collect analytics events:

```kotlin title="build.gradle.kts" linenums="1"
dependencies {
  implementation("com.addhen.kanalytics:kanalytics:1.0.0")
}
```

To view the analytics events, being sent to tracking tools, add the `kanalytics-viewer` artifact:

**Note:** You might want to add this to your non production build type as you don't want to ship the viewer to production.

```kotlin title="build.gradle.kts" linenums="1"
dependencies {
  if (buildType.name == "debug") {
    implementation("com.addhen.kanalytics:kanalytics-viewer:1.0.0")
  } else {
    implementation("com.addhen.kanalytics:kanalytics-viewer-no-op:1.0.0")
  }

  //On Android, it will simply be
  debugimplementation("com.addhen.kanalytics:kanalytics-viewer:1.0.0")
  releaseimplementation("com.addhen.kanalytics:kanalytics-viewer-no-op:1.0.0")
}
```

<details>
<summary>Snapshots of the development version are available in Sonatype's snapshots repository.</summary>
<p>

```groovy title="build.gradle.kts" linenums="1"
repository {
  mavenCentral()
  maven {
    url 'https://oss.sonatype.org/content/repositories/snapshots/'
  }
}

dependencies {
  implementation("com.addhen.kanalytics:kanalytics:1.0.0-SNAPSHOT")
  if (buildType.name == "debug") {
    implementation("com.addhen.kanalytics:kanalytics-viewer:1.0.0-SNAPSHOT")
  } else {
    implementation("com.addhen.kanalytics:kanalytics-viewer-no-op:1.0.0-SNAPSHOT")
  }
}
```
</p>
</details>
