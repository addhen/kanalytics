// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.theme

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
internal actual fun colorScheme(useDarkColors: Boolean, useDynamicColors: Boolean): ColorScheme =
  when {
    Build.VERSION.SDK_INT >= 31 && useDynamicColors && useDarkColors -> {
      dynamicDarkColorScheme(LocalContext.current)
    }
    Build.VERSION.SDK_INT >= 31 && useDynamicColors && !useDarkColors -> {
      dynamicLightColorScheme(LocalContext.current)
    }
    useDarkColors -> lightScheme
    else -> darkScheme
  }
