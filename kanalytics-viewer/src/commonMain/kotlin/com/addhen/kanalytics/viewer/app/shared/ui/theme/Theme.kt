// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

// Light scheme

private val lightScheme = lightColorScheme(
  primary = primaryLight,
  onPrimary = onPrimaryLight,
  primaryContainer = primaryContainerLight,
  onPrimaryContainer = onPrimaryContainerLight,
  secondary = secondaryLight,
  onSecondary = onSecondaryLight,
  secondaryContainer = secondaryContainerLight,
  onSecondaryContainer = onSecondaryContainerLight,
  tertiary = tertiaryLight,
  onTertiary = onTertiaryLight,
  tertiaryContainer = tertiaryContainerLight,
  onTertiaryContainer = onTertiaryContainerLight,
  error = errorLight,
  onError = onErrorLight,
  errorContainer = errorContainerLight,
  onErrorContainer = onErrorContainerLight,
  background = backgroundLight,
  onBackground = onBackgroundLight,
  surface = surfaceLight,
  onSurface = onSurfaceLight,
  surfaceVariant = surfaceVariantLight,
  onSurfaceVariant = onSurfaceVariantLight,
  outline = outlineLight,
  outlineVariant = outlineVariantLight,
  scrim = scrimLight,
  inverseSurface = inverseSurfaceLight,
  inverseOnSurface = inverseOnSurfaceLight,
  inversePrimary = inversePrimaryLight,
  surfaceDim = surfaceDimLight,
  surfaceBright = surfaceBrightLight,
  surfaceContainerLowest = surfaceContainerLowestLight,
  surfaceContainerLow = surfaceContainerLowLight,
  surfaceContainer = surfaceContainerLight,
  surfaceContainerHigh = surfaceContainerHighLight,
  surfaceContainerHighest = surfaceContainerHighestLight,
)

private val mediumContrastLightColorScheme = lightColorScheme(
  primary = primaryLightMediumContrast,
  onPrimary = onPrimaryLightMediumContrast,
  primaryContainer = primaryContainerLightMediumContrast,
  onPrimaryContainer = onPrimaryContainerLightMediumContrast,
  secondary = secondaryLightMediumContrast,
  onSecondary = onSecondaryLightMediumContrast,
  secondaryContainer = secondaryContainerLightMediumContrast,
  onSecondaryContainer = onSecondaryContainerLightMediumContrast,
  tertiary = tertiaryLightMediumContrast,
  onTertiary = onTertiaryLightMediumContrast,
  tertiaryContainer = tertiaryContainerLightMediumContrast,
  onTertiaryContainer = onTertiaryContainerLightMediumContrast,
  error = errorLightMediumContrast,
  onError = onErrorLightMediumContrast,
  errorContainer = errorContainerLightMediumContrast,
  onErrorContainer = onErrorContainerLightMediumContrast,
  background = backgroundLightMediumContrast,
  onBackground = onBackgroundLightMediumContrast,
  surface = surfaceLightMediumContrast,
  onSurface = onSurfaceLightMediumContrast,
  surfaceVariant = surfaceVariantLightMediumContrast,
  onSurfaceVariant = onSurfaceVariantLightMediumContrast,
  outline = outlineLightMediumContrast,
  outlineVariant = outlineVariantLightMediumContrast,
  scrim = scrimLightMediumContrast,
  inverseSurface = inverseSurfaceLightMediumContrast,
  inverseOnSurface = inverseOnSurfaceLightMediumContrast,
  inversePrimary = inversePrimaryLightMediumContrast,
  surfaceDim = surfaceDimLightMediumContrast,
  surfaceBright = surfaceBrightLightMediumContrast,
  surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
  surfaceContainerLow = surfaceContainerLowLightMediumContrast,
  surfaceContainer = surfaceContainerLightMediumContrast,
  surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
  surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
  primary = primaryLightHighContrast,
  onPrimary = onPrimaryLightHighContrast,
  primaryContainer = primaryContainerLightHighContrast,
  onPrimaryContainer = onPrimaryContainerLightHighContrast,
  secondary = secondaryLightHighContrast,
  onSecondary = onSecondaryLightHighContrast,
  secondaryContainer = secondaryContainerLightHighContrast,
  onSecondaryContainer = onSecondaryContainerLightHighContrast,
  tertiary = tertiaryLightHighContrast,
  onTertiary = onTertiaryLightHighContrast,
  tertiaryContainer = tertiaryContainerLightHighContrast,
  onTertiaryContainer = onTertiaryContainerLightHighContrast,
  error = errorLightHighContrast,
  onError = onErrorLightHighContrast,
  errorContainer = errorContainerLightHighContrast,
  onErrorContainer = onErrorContainerLightHighContrast,
  background = backgroundLightHighContrast,
  onBackground = onBackgroundLightHighContrast,
  surface = surfaceLightHighContrast,
  onSurface = onSurfaceLightHighContrast,
  surfaceVariant = surfaceVariantLightHighContrast,
  onSurfaceVariant = onSurfaceVariantLightHighContrast,
  outline = outlineLightHighContrast,
  outlineVariant = outlineVariantLightHighContrast,
  scrim = scrimLightHighContrast,
  inverseSurface = inverseSurfaceLightHighContrast,
  inverseOnSurface = inverseOnSurfaceLightHighContrast,
  inversePrimary = inversePrimaryLightHighContrast,
  surfaceDim = surfaceDimLightHighContrast,
  surfaceBright = surfaceBrightLightHighContrast,
  surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
  surfaceContainerLow = surfaceContainerLowLightHighContrast,
  surfaceContainer = surfaceContainerLightHighContrast,
  surfaceContainerHigh = surfaceContainerHighLightHighContrast,
  surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

// Dark scheme
private val darkScheme = darkColorScheme(
  primary = primaryDark,
  onPrimary = onPrimaryDark,
  primaryContainer = primaryContainerDark,
  onPrimaryContainer = onPrimaryContainerDark,
  secondary = secondaryDark,
  onSecondary = onSecondaryDark,
  secondaryContainer = secondaryContainerDark,
  onSecondaryContainer = onSecondaryContainerDark,
  tertiary = tertiaryDark,
  onTertiary = onTertiaryDark,
  tertiaryContainer = tertiaryContainerDark,
  onTertiaryContainer = onTertiaryContainerDark,
  error = errorDark,
  onError = onErrorDark,
  errorContainer = errorContainerDark,
  onErrorContainer = onErrorContainerDark,
  background = backgroundDark,
  onBackground = onBackgroundDark,
  surface = surfaceDark,
  onSurface = onSurfaceDark,
  surfaceVariant = surfaceVariantDark,
  onSurfaceVariant = onSurfaceVariantDark,
  outline = outlineDark,
  outlineVariant = outlineVariantDark,
  scrim = scrimDark,
  inverseSurface = inverseSurfaceDark,
  inverseOnSurface = inverseOnSurfaceDark,
  inversePrimary = inversePrimaryDark,
  surfaceDim = surfaceDimDark,
  surfaceBright = surfaceBrightDark,
  surfaceContainerLowest = surfaceContainerLowestDark,
  surfaceContainerLow = surfaceContainerLowDark,
  surfaceContainer = surfaceContainerDark,
  surfaceContainerHigh = surfaceContainerHighDark,
  surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
  primary = primaryDarkMediumContrast,
  onPrimary = onPrimaryDarkMediumContrast,
  primaryContainer = primaryContainerDarkMediumContrast,
  onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
  secondary = secondaryDarkMediumContrast,
  onSecondary = onSecondaryDarkMediumContrast,
  secondaryContainer = secondaryContainerDarkMediumContrast,
  onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
  tertiary = tertiaryDarkMediumContrast,
  onTertiary = onTertiaryDarkMediumContrast,
  tertiaryContainer = tertiaryContainerDarkMediumContrast,
  onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
  error = errorDarkMediumContrast,
  onError = onErrorDarkMediumContrast,
  errorContainer = errorContainerDarkMediumContrast,
  onErrorContainer = onErrorContainerDarkMediumContrast,
  background = backgroundDarkMediumContrast,
  onBackground = onBackgroundDarkMediumContrast,
  surface = surfaceDarkMediumContrast,
  onSurface = onSurfaceDarkMediumContrast,
  surfaceVariant = surfaceVariantDarkMediumContrast,
  onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
  outline = outlineDarkMediumContrast,
  outlineVariant = outlineVariantDarkMediumContrast,
  scrim = scrimDarkMediumContrast,
  inverseSurface = inverseSurfaceDarkMediumContrast,
  inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
  inversePrimary = inversePrimaryDarkMediumContrast,
  surfaceDim = surfaceDimDarkMediumContrast,
  surfaceBright = surfaceBrightDarkMediumContrast,
  surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
  surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
  surfaceContainer = surfaceContainerDarkMediumContrast,
  surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
  surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
  primary = primaryDarkHighContrast,
  onPrimary = onPrimaryDarkHighContrast,
  primaryContainer = primaryContainerDarkHighContrast,
  onPrimaryContainer = onPrimaryContainerDarkHighContrast,
  secondary = secondaryDarkHighContrast,
  onSecondary = onSecondaryDarkHighContrast,
  secondaryContainer = secondaryContainerDarkHighContrast,
  onSecondaryContainer = onSecondaryContainerDarkHighContrast,
  tertiary = tertiaryDarkHighContrast,
  onTertiary = onTertiaryDarkHighContrast,
  tertiaryContainer = tertiaryContainerDarkHighContrast,
  onTertiaryContainer = onTertiaryContainerDarkHighContrast,
  error = errorDarkHighContrast,
  onError = onErrorDarkHighContrast,
  errorContainer = errorContainerDarkHighContrast,
  onErrorContainer = onErrorContainerDarkHighContrast,
  background = backgroundDarkHighContrast,
  onBackground = onBackgroundDarkHighContrast,
  surface = surfaceDarkHighContrast,
  onSurface = onSurfaceDarkHighContrast,
  surfaceVariant = surfaceVariantDarkHighContrast,
  onSurfaceVariant = onSurfaceVariantDarkHighContrast,
  outline = outlineDarkHighContrast,
  outlineVariant = outlineVariantDarkHighContrast,
  scrim = scrimDarkHighContrast,
  inverseSurface = inverseSurfaceDarkHighContrast,
  inverseOnSurface = inverseOnSurfaceDarkHighContrast,
  inversePrimary = inversePrimaryDarkHighContrast,
  surfaceDim = surfaceDimDarkHighContrast,
  surfaceBright = surfaceBrightDarkHighContrast,
  surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
  surfaceContainerLow = surfaceContainerLowDarkHighContrast,
  surfaceContainer = surfaceContainerDarkHighContrast,
  surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
  surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

internal sealed class EventsTableColorScheme {
  abstract val textColor: Color

  data object Light : EventsTableColorScheme() {
    override val textColor = eventHeaderLight
  }

  data object MediumConstrastLight : EventsTableColorScheme() {
    override val textColor = eventHeaderLightMediumContrast
  }

  data object HighContrastLight : EventsTableColorScheme() {
    override val textColor = eventHeaderLightHighContrast
  }

  data object Dark : EventsTableColorScheme() {
    override val textColor = eventHeaderDark
  }

  data object MediumContrastDark : EventsTableColorScheme() {
    override val textColor = eventHeaderDarkMediumContrast
  }

  data object HighContrastDark : EventsTableColorScheme() {
    override val textColor = eventHeaderDarkHighContrast
  }
}

internal sealed class EventNameColorScheme {
  abstract val textColor: Color

  data object Light : EventNameColorScheme() {
    override val textColor = eventNameLight
  }

  data object MediumContrastLight : EventNameColorScheme() {
    override val textColor = eventNameLightMediumContrast
  }

  data object HighContrastLight : EventNameColorScheme() {
    override val textColor = eventNameLightHighContrast
  }

  data object Dark : EventNameColorScheme() {
    override val textColor = eventNameDark
  }

  data object MediumContrastDark : EventNameColorScheme() {
    override val textColor = eventNameDarkMediumContrast
  }

  data object HighContrastDark : EventNameColorScheme() {
    override val textColor = eventNameDarkHighContrast
  }
}

internal sealed class EventPropertiesColorScheme {
  abstract val textColor: Color

  data object Light : EventPropertiesColorScheme() {
    override val textColor = eventPropertiesLight
  }

  data object MediumContrastLight : EventPropertiesColorScheme() {
    override val textColor = eventPropertiesLightMediumContrast
  }

  data object HighContrastLight : EventPropertiesColorScheme() {
    override val textColor = eventPropertiesLightHighContrast
  }

  data object Dark : EventPropertiesColorScheme() {
    override val textColor = eventPropertiesDark
  }

  data object MediumContrastDark : EventPropertiesColorScheme() {
    override val textColor = eventPropertiesDarkMediumContrast
  }

  data object HighContrastDark : EventPropertiesColorScheme() {
    override val textColor = eventPropertiesDarkHighContrast
  }
}

@Composable
internal fun eventHeaderTextColor(
  colorContrast: ColorContrast = ColorContrast.Dark,
  isDarkTheme: Boolean = isSystemInDarkTheme()
) = when {
  isDarkTheme && colorContrast == ColorContrast.Light -> EventsTableColorScheme.Light.textColor
  isDarkTheme && colorContrast == ColorContrast.LightMedium -> EventsTableColorScheme.Light.textColor
  isDarkTheme && colorContrast == ColorContrast.LightHigh -> EventsTableColorScheme.Light.textColor
  isDarkTheme && colorContrast ==ColorContrast.Dark -> EventsTableColorScheme.Dark.textColor
  isDarkTheme && colorContrast == ColorContrast.DarkMedium -> EventsTableColorScheme.MediumContrastDark.textColor
  isDarkTheme && colorContrast ==ColorContrast.DarkHigh -> EventsTableColorScheme.HighContrastDark.textColor
  else -> {
    EventsTableColorScheme.Light.textColor
  }
}

@Composable
internal fun eventNameTextColor(
  colorContrast: ColorContrast = ColorContrast.Dark,
  isDarkTheme: Boolean = isSystemInDarkTheme()
) = when {
  isDarkTheme && colorContrast == ColorContrast.Light -> EventNameColorScheme.Light.textColor
  isDarkTheme && colorContrast == ColorContrast.LightMedium -> EventNameColorScheme.Light.textColor
  isDarkTheme && colorContrast == ColorContrast.LightHigh -> EventNameColorScheme.Light.textColor
  isDarkTheme && colorContrast ==ColorContrast.Dark -> EventNameColorScheme.Dark.textColor
  isDarkTheme && colorContrast == ColorContrast.DarkMedium -> EventNameColorScheme.MediumContrastDark.textColor
  isDarkTheme && colorContrast ==ColorContrast.DarkHigh -> EventNameColorScheme.HighContrastDark.textColor
  else -> {
    EventNameColorScheme.Light.textColor
  }
}

@Composable
internal fun eventPropertiesTextColor(
  colorContrast: ColorContrast = ColorContrast.Dark,
  isDarkTheme: Boolean = isSystemInDarkTheme()
) = when {
  isDarkTheme && colorContrast == ColorContrast.Light -> EventPropertiesColorScheme.Light.textColor
  isDarkTheme && colorContrast == ColorContrast.LightMedium -> EventPropertiesColorScheme.Light.textColor
  isDarkTheme && colorContrast == ColorContrast.LightHigh -> EventPropertiesColorScheme.Light.textColor
  isDarkTheme && colorContrast ==ColorContrast.Dark -> EventPropertiesColorScheme.Dark.textColor
  isDarkTheme && colorContrast == ColorContrast.DarkMedium -> EventPropertiesColorScheme.MediumContrastDark.textColor
  isDarkTheme && colorContrast ==ColorContrast.DarkHigh -> EventPropertiesColorScheme.HighContrastDark.textColor
  else -> {
    EventPropertiesColorScheme.Light.textColor
  }
}

@Composable
internal fun AppTheme(
  isDarkTheme: Boolean = isSystemInDarkTheme(),
  colorContrast: ColorContrast = if (isDarkTheme) ColorContrast.Dark else ColorContrast.Light,
  fontFamily: FontFamily? = jetbrainsMonoFontFamily(),
  content: @Composable () -> Unit,
) {

    val colorScheme = getColorScheme(colorContrast, isDarkTheme)

  MaterialTheme(
    colorScheme = colorScheme,
    typography = appTypography(fontFamily),
    content = content,
  )
}

private fun getColorScheme(colorContrast: ColorContrast, darkTheme: Boolean) = when {
  darkTheme && colorContrast == ColorContrast.Dark -> darkScheme
  darkTheme && colorContrast == ColorContrast.DarkMedium -> mediumContrastDarkColorScheme
  darkTheme && colorContrast == ColorContrast.DarkHigh -> highContrastDarkColorScheme
  darkTheme -> darkScheme
  !darkTheme && colorContrast == ColorContrast.Light -> lightScheme
  !darkTheme && colorContrast == ColorContrast.LightMedium -> mediumContrastLightColorScheme
  !darkTheme && colorContrast == ColorContrast.LightHigh -> highContrastLightColorScheme
  else -> lightScheme
}

internal enum class ColorContrast {
  Light,
  LightMedium,
  LightHigh,
  Dark,
  DarkMedium,
  DarkHigh,
}
