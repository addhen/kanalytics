package com.addhen.kanalytics.sample.shared.trackers

import co.touchlab.kermit.Logger
import com.addhen.kanalytics.KAnalyticsEvent
import com.addhen.kanalytics.Tracker

public class FirebaseTracker : Tracker {

  private val analyticsEvents = mutableListOf<KAnalyticsEvent>()

  override fun send(event: KAnalyticsEvent) {
    // Send event to firebase
    analyticsEvents.add(event)
    Logger.d(tag = "MainActivity", messageString = "FirebaseTracker: $event")
  }
}
