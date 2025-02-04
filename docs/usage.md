Usage
=====

This guide explains how to integrate and use the `KAnalytics`
library for event tracking in your applications.

# Basic Setup

## 1. Creating Trackers

A tracker sends events to a specific analytics service.
Implement the `KTracker` interface to create a custom tracker:

```kotlin linenums="1"

class FirebaseTracker(private val context: Context) : KTracker {

  override fun send(event: KAnalyticsEvent) {
    FirebaseAnalytics.getInstance(context).logEvent(
      event.eventName,
      event.properties
    )
  }
}

```

## 2. Creating Interceptors

Interceptors process events before they reach the trackers.
They can modify, filter, or log events. Implement the
`Interceptor` interface to create a custom interceptor:

```kotlin linenums="1"
class LogInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): KAnalyticsEvent {
    val modifiedEvent = chain.event.copy(
      eventName = "Modified: ${chain.event.eventName}"
    )
    return chain.proceed(modifiedEvent)
  }
}
```

## 3. Initializing KAnalytics
Configure KAnalytics with your trackers and interceptors:

```kotlin linenums="1"
val kAnalytics = KAnalytics.Builder()
  .addTracker(FirebaseTracker(App.applicationContext()))
  .addTracker(AmplitudeTracker())
  .addInterceptor(LogInterceptor())
  .build()
```

# Sending Events

## Send to All Trackers

```kotlin linenums="1"
val event = KAnalyticsEvent("user_login").apply {
  addParameters(
    mapOf(
      "screen_name" to "LoginScreen",
      "user_type" to "new_user"
    )
  )
}

kAnalytics.send(event)  // Sends to all trackers
```

## Send to Specific Tracker

```kotlin linenums="1"

kAnalytics.send(event, FirebaseTracker::class)  // Sends only to Firebase
```

# Debug Tools

## KAnalyticsViewer Setup

If you're using `KAnalyticsViewer` to view the events to be sent and wants to configure its Interceptor:


```kotlin linenums="1"
// Configure event collection
val collector = KAnalyticsCollector(
  showNotification = true,
  duration = RetentionPolicyManager.DayDuration(7)  // Keep events for 7 days
)

// Create and add viewer interceptor
val viewerInterceptor = KAnalyticsInterceptor(collector = collector)

val kAnalytics = KAnalytics.Builder()
  .addTracker(FirebaseTracker(App.applicationContext()))
  .addInterceptor(viewerInterceptor)
  .build()
```
