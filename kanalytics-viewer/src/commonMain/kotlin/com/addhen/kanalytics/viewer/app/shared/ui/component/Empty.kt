package com.addhen.kanalytics.viewer.app.shared.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
public fun EmptyContent(
  modifier: Modifier = Modifier,
  title: @Composable () -> Unit,
) {
  Box(modifier = modifier) {
    Column(modifier = Modifier.align(Alignment.Center)) {
      ProvideTextStyle(MaterialTheme.typography.headlineMedium) { title() }
    }
  }
}
