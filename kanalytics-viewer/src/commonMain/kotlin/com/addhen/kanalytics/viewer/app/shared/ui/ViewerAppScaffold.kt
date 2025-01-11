// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.addhen.kanalytics.viewer.app.shared.ui.component.AppSnackbarHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun ViewerAppScaffold(
  title: String,
  navigationIcon: @Composable () -> Unit = {},
  actions: @Composable RowScope.() -> Unit = {},
  searchTopBar: @Composable () -> Unit = {},
  topBar: @Composable () -> Unit = {
    Column(modifier = Modifier.fillMaxWidth()) {
      CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = Color.Transparent,
          scrolledContainerColor = Color.Transparent,
        ),
        actions = actions,
        modifier = Modifier.fillMaxWidth(),
      )
      searchTopBar()
    }
  },
  snackbarHostState: SnackbarHostState,
  content: @Composable BoxScope.() -> Unit,
) {
  Scaffold(
    topBar = topBar,
    modifier = Modifier.fillMaxSize(),
    snackbarHost = { AppSnackbarHost(hostState = snackbarHostState) },
  ) { contentPadding ->

    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(contentPadding)
        .consumeWindowInsets(contentPadding)
        .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)),
      content = content,
    )
  }
}
