// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer

import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.addhen.kanalytics.viewer.app.android.ContextInitializer
import com.addhen.kanalytics.viewer.app.android.MainActivity

private const val SHORTCUT_ID = "kanalytics_viewer_shortcut"
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

internal actual fun setupShortcut(shouldCreateShortcut: Boolean) {
  if (shouldCreateShortcut) {
    createShortcut()
  } else {
    ShortcutManagerCompat.removeDynamicShortcuts(
      ContextInitializer.applicationContext,
      listOf(SHORTCUT_ID),
    )
  }
}

private fun createShortcut() {
  val shortcut = ShortcutInfoCompat.Builder(ContextInitializer.applicationContext, SHORTCUT_ID)
    .setShortLabel(ContextInitializer.applicationContext.getText(R.string.viewer_app_name))
    .setLongLabel(ContextInitializer.applicationContext.getText(R.string.shortcut_long_label))
    .setIcon(
      IconCompat.createWithResource(ContextInitializer.applicationContext, R.drawable.ic_app_icon),
    )
    .setIntent(
      Intent(ContextInitializer.applicationContext, MainActivity::class.java)
        .also { intent ->
          intent.action = Intent.ACTION_VIEW
          intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        },
    )
    .build()

  ShortcutManagerCompat.pushDynamicShortcut(ContextInitializer.applicationContext, shortcut)
}
