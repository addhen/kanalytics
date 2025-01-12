package com.addhen.kanalytics.viewer.app.shared.ui.theme

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.JetBrainsMono_Italic_VariableFont_wght
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.JetBrainsMono_VariableFont_wght
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.Res

@Composable
internal actual fun jetbrainsMonoFontFamily(): FontFamily = FontFamily(
  Font(Res.font.JetBrainsMono_VariableFont_wght),
  Font(Res.font.JetBrainsMono_Italic_VariableFont_wght),
)
