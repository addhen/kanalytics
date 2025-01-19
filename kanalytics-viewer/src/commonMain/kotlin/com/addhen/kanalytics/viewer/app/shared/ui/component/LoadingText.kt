// Copyright 2023, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.Res
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.loading
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LoadingText(
  modifier: Modifier = Modifier,
  text: String = stringResource(Res.string.loading),
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(
      space = 16.dp,
      alignment = Alignment.CenterVertically,
    ),
  ) {
    CircularProgressIndicator()

    Text(
      modifier = Modifier.fillMaxWidth(),
      text = text,
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyMedium,
      overflow = TextOverflow.Ellipsis,
    )
  }
}
