// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

internal fun AnnotatedString.Builder.toStyledKeyValueString(key: String, value: String?) {
  value ?: return

  withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
    append(key)
    append(": ")
  }
  append("$value\n")
}
