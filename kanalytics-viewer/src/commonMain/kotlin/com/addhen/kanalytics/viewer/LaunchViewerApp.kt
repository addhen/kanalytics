// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer

public expect fun launchViewerApp()

internal expect fun disposeViewerAppWindow()

// Create a ShortcutManager interface
public interface ShortcutManager {
  public fun setupShortcut(show: Boolean)
}

// Default implementation that will be used in Android
public expect class DefaultShortcutManager() : ShortcutManager {
  override fun setupShortcut(show: Boolean)
}
