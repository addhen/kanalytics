package com.addhen.kanalytics.viewer.app.shared.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
internal fun appTypography(): Typography {

  return Typography(
    displayLarge = TextStyle(
      fontSize = 57.sp,
      lineHeight = 64.sp,
      letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
      fontSize = 45.sp,
      lineHeight = 52.sp,
      letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
      fontSize = 36.sp,
      lineHeight = 44.sp,
      letterSpacing = 0.sp,
    ),
    headlineLarge = TextStyle(
      fontSize = 32.sp,
      lineHeight = 40.sp,
      letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
      fontSize = 28.sp,
      lineHeight = 36.sp,
      letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
      fontSize = 24.sp,
      lineHeight = 32.sp,
      letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
      fontSize = 22.sp,
      lineHeight = 28.sp,
      letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
      fontSize = 16.sp,
      lineHeight = 24.sp,
      letterSpacing = 0.15.sp,
    ),
    titleSmall = TextStyle(
      fontSize = 14.sp,
      lineHeight = 20.sp,
      letterSpacing = 0.1.sp,
    ),
    labelLarge = TextStyle(
      fontWeight = FontWeight.Medium,
      fontSize = 14.sp,
      lineHeight = 20.sp,
      letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
      fontWeight = FontWeight.Medium,
      fontSize = 12.sp,
      lineHeight = 16.sp,
      letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
      fontWeight = FontWeight.Medium,
      fontSize = 11.sp,
      lineHeight = 16.sp,
      letterSpacing = 0.5.sp,
    ),
    bodyLarge = TextStyle(
      fontSize = 16.sp,
      lineHeight = 24.sp,
      letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
      fontSize = 14.sp,
      lineHeight = 20.sp,
      letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
      fontSize = 12.sp,
      lineHeight = 16.sp,
      letterSpacing = 0.4.sp,
    ),
  )
}
