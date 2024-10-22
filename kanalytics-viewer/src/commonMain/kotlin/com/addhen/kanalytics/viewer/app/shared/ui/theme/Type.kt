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
      fontWeight = FontWeight.W400,
      fontStyle = FontStyle.Normal,
      lineHeight = 64.sp,
    ),
    displayMedium = TextStyle(
      fontSize = 45.sp,
      fontWeight = FontWeight.W400,
      fontStyle = FontStyle.Normal,
      lineHeight = 52.sp,
    ),
    displaySmall = TextStyle(
      fontSize = 36.sp,
      fontWeight = FontWeight.W400,
      fontStyle = FontStyle.Normal,
      lineHeight = 44.sp,
    ),
    headlineLarge = TextStyle(
      fontSize = 32.sp,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 40.sp,
    ),
    headlineMedium = TextStyle(
      fontSize = 28.sp,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 36.sp,
    ),
    headlineSmall = TextStyle(
      fontSize = 24.sp,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 32.sp,
    ),
    titleLarge = TextStyle(
      fontSize = 22.sp,
      fontWeight = FontWeight.W400,
      fontStyle = FontStyle.Normal,
      lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
      fontSize = 16.sp,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 24.sp,
      letterSpacing = 0.15.sp,
    ),
    titleSmall = TextStyle(
      fontSize = 14.sp,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 20.sp,
      letterSpacing = 0.1.sp,
    ),
    labelLarge = TextStyle(
      fontSize = 14.sp,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 20.sp,
      letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
      fontSize = 12.sp,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 16.sp,
      letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
      fontSize = 11.sp,
      fontWeight = FontWeight.W500,
      fontStyle = FontStyle.Normal,
      lineHeight = 16.sp,
      letterSpacing = 0.5.sp,
    ),
    bodyLarge = TextStyle(
      fontSize = 16.sp,
      fontWeight = FontWeight.W400,
      fontStyle = FontStyle.Normal,
      lineHeight = 24.sp,
      letterSpacing = 0.15.sp,
    ),
    bodyMedium = TextStyle(
      fontSize = 14.sp,
      fontWeight = FontWeight.W400,
      fontStyle = FontStyle.Normal,
      lineHeight = 20.sp,
      letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
      fontSize = 12.sp,
      fontWeight = FontWeight.W400,
      fontStyle = FontStyle.Normal,
      lineHeight = 16.sp,
      letterSpacing = 0.4.sp,
    ),
  )
}
