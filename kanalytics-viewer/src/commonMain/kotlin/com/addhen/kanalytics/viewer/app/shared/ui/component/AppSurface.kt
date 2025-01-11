// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public fun AppSurface(appNavHost: @Composable () -> Unit) {
  AppBackground(modifier = Modifier.fillMaxSize()) {
    AppFrame(appNavHost)
  }
}

@Composable
private fun AppFrame(appNavHost: @Composable () -> Unit) {
  appNavHost()
}

@Composable
private fun AppBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
  Surface(
    color = MaterialTheme.colorScheme.background,
    modifier = modifier,
    content = content,
  )
}
