// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.JetBrainsMono_Italic_VariableFont_wght
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.JetBrainsMono_VariableFont_wght
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
internal actual fun jetbrainsMonoFontFamily(): FontFamily = FontFamily(
  Font(Res.font.JetBrainsMono_VariableFont_wght),
  Font(Res.font.JetBrainsMono_Italic_VariableFont_wght),
)
