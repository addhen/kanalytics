// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.Res
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.cancel
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.confirm
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ConfirmationDialog(
  title: String,
  message: String,
  confirmButtonText: String = stringResource(Res.string.confirm),
  dismissButtonText: String = stringResource(Res.string.cancel),
  onConfirm: () -> Unit,
  onDismiss: () -> Unit,
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center,
      )
    },
    text = {
      Text(
        text = message,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    },
    confirmButton = {
      TextButton(onClick = onConfirm) {
        Text(text = confirmButtonText)
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text(text = dismissButtonText)
      }
    },
  )
}
