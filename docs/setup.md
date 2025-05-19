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
  implementation("com.addhen.kanalytics:kanalytics:x.y.z")
}
```

To view the analytics events, being sent to tracking tools, add the `kanalytics-viewer` artifact:

**Note:** You might want to add this to your non production build type as you don't want to ship the viewer to production.

```kotlin title="build.gradle.kts" linenums="1"
dependencies {
  if (buildType.name == "debug") {
    implementation("com.addhen.kanalytics:kanalytics-viewer:x.y.z")
  } else {
    implementation("com.addhen.kanalytics:kanalytics-viewer-no-op:x.y.z")
  }

  //On Android, it will simply be
  debugimplementation("com.addhen.kanalytics:kanalytics-viewer:x.y.z")
  releaseimplementation("com.addhen.kanalytics:kanalytics-viewer-no-op:x.y.z")
}
```

<details>
<summary>Snapshots of the development version are available in the Central Portal Snapshots repository.</summary>
<p>

```kotlin title="build.gradle.kts" linenums="1"
repository {
  mavenCentral()
  maven {
    url 'https://central.sonatype.com/repository/maven-snapshots/'
  }
}

dependencies {
  implementation("com.addhen.kanalytics:kanalytics:x.y.z-SNAPSHOT")
  if (buildType.name == "debug") {
    implementation("com.addhen.kanalytics:kanalytics-viewer:x.y.z-SNAPSHOT")
  } else {
    implementation("com.addhen.kanalytics:kanalytics-viewer-no-op:x.y.z-SNAPSHOT")
  }
}
```
</p>
</details>


## Setup Quick Actions on iOS

The quick actions support on iOS requires some manual setup before you can use it.


### Install dependency

Export the `kanalytics-viewer` to used in your swift project:

```kotlin title="ios-framework/build.gradle.kts" linenums="1"

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        // Existing dependencies...
        // `api` is important here to allow it be exported in the binary framework below
        api(projects.kanalyticsViewer)
      }
    }

    targets.withType<KotlinNativeTarget>().configureEach {
      binaries.framework {
        isStatic = true
        baseName = "KAnalyticsViewerKt"
        export(projects.kanalyticsViewer)
      }
    }
  }
}
```

### Handle Quick Actions in AppDelegate
Add Quick Actions support by implementing the necessary delegate method in your `AppDelegate` class:

```swift title="AppDelegate.swift" linenums="1"
import KAnalyticsKt

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        configurationForConnecting connectingSceneSession: UISceneSession,
        options: UIScene.ConnectionOptions
    ) -> UISceneConfiguration {
        return KAnalyticsViewerShorcutHandlerKt.getUISceneConfiguration(configurationForConnectingSceneSession: connectingSceneSession)
    }
}
```

For a sample implementation see the sample [iOS app](../sample/ios/iosApp/AppDelegate.swift).
