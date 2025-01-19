// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import android.content.Intent
import com.addhen.kanalytics.viewer.app.android.ContextInitializer
import com.addhen.kanalytics.viewer.app.android.MainActivity

public actual fun launchViewerApp() {
  val context = ContextInitializer.applicationContext
  context.startActivity(
    Intent(context, MainActivity::class.java)
      .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
  )
}

internal actual fun disposeViewerAppWindow() {
  MainActivity.viewerAppMainActivityInstance = null
}
