// Copyright 2024, Addhen Ltd and the k-location project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import co.touchlab.kermit.Logger
import com.addhen.kanalytics.KAnalytics
import com.addhen.kanalytics.Tracker
import com.addhen.kanalytics.interceptor.KAnalyticsEvent
import com.addhen.kanalytics.viewer.KAnalyticsInterceptor
import com.addhen.kanalytics.sample.shared.LocationScreen
import com.addhen.kanalytics.sample.shared.SampleApp
import com.addhen.kanalytics.sample.shared.SampleViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge()
    super.onCreate(savedInstanceState)
    setContent {
      val navController = rememberNavController()
      SampleApp(
        navController = navController
      ) {
        val kanalytics = KAnalytics.Builder()
        .addTracker(FirebaseTracker())
        .addInterceptor(KAnalyticsInterceptor())
        .build()

        val viewModel = LocationViewModelFactory(
          kanalytics = kanalytics
        ).create(SampleViewModel::class.java)
        LocationScreen(
          viewModel
        ) {
          startActivity(
            Intent(this, com.addhen.kanalytics.viewer.android.MainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
      }
    }
  }
}

class FirebaseTracker : Tracker {

  private val analyticsEvents = mutableListOf<KAnalyticsEvent>()

  override fun send(event: KAnalyticsEvent) {
    // Send event to firebase
    analyticsEvents.add(event)
    Logger.d(tag = "MainActivity", messageString = "FirebaseTracker: $event")
  }
}
