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
import com.sebastianneubauer.jsontree.TreeColors
import com.sebastianneubauer.jsontree.defaultLightColors

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

internal sealed class EventsTableColorScheme {
  abstract val textColor: Color

  data object Light : EventsTableColorScheme() {
    override val textColor = eventHeaderLight
  }

  data object Dark : EventsTableColorScheme() {
    override val textColor = eventHeaderDark
  }
}

internal sealed class EventNameColorScheme {
  abstract val textColor: Color

  data object Light : EventNameColorScheme() {
    override val textColor = eventNameLight
  }

  data object Dark : EventNameColorScheme() {
    override val textColor = eventNameDark
  }
}

internal sealed class EventPropertiesColorScheme {
  abstract val textColor: Color

  data object Light : EventPropertiesColorScheme() {
    override val textColor = eventPropertiesLight
  }

  data object Dark : EventPropertiesColorScheme() {
    override val textColor = eventPropertiesDark
  }

}

internal sealed class SearchHighlightColorScheme {
  abstract val textColor: Color

  data object Light : SearchHighlightColorScheme() {
    override val textColor = searchHighlightLight
  }

  data object Dark : SearchHighlightColorScheme() {
    override val textColor = searchHighlightDark
  }
}

@Composable
internal fun eventHeaderTextColor(
  isDarkTheme: Boolean = isSystemInDarkTheme()
) = when {
  isDarkTheme -> EventsTableColorScheme.Dark.textColor
  else -> EventsTableColorScheme.Light.textColor
}

@Composable
internal fun eventNameTextColor(
  isDarkTheme: Boolean = isSystemInDarkTheme()
) = when {
  isDarkTheme -> EventNameColorScheme.Dark.textColor
  else -> {
    EventNameColorScheme.Light.textColor
  }
}

@Composable
internal fun eventPropertiesTextColor(
  isDarkTheme: Boolean = isSystemInDarkTheme()
) = when {
  isDarkTheme  -> EventPropertiesColorScheme.Dark.textColor
  else -> {
    EventPropertiesColorScheme.Light.textColor
  }
}

@Composable
internal fun jsonTreeTextColor(
  isDarkTheme: Boolean = isSystemInDarkTheme()
) = when {
  isDarkTheme -> {
    TreeColors(
      keyColor = defaultLightColors.keyColor,
      stringValueColor = defaultLightColors.stringValueColor,
      numberValueColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.4f),
      booleanValueColor = defaultLightColors.booleanValueColor,
      nullValueColor = defaultLightColors.nullValueColor,
      indexColor = defaultLightColors.indexColor,
      symbolColor = MaterialTheme.colorScheme.onSecondaryContainer,
      iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
      highlightColor = searchHighlightDark,
      selectedHighlightColor = searchHighlightDark,
    )
  } else -> {
    TreeColors(
      keyColor = defaultLightColors.keyColor,
      stringValueColor = defaultLightColors.stringValueColor,
      numberValueColor = defaultLightColors.numberValueColor,
      booleanValueColor = defaultLightColors.booleanValueColor,
      nullValueColor = defaultLightColors.nullValueColor,
      indexColor = defaultLightColors.indexColor,
      symbolColor = defaultLightColors.symbolColor,
      iconColor = defaultLightColors.iconColor,
      highlightColor = searchHighlightLight,
      selectedHighlightColor = searchHighlightLight,
    )
  }
}

@Composable
internal fun searchHighlightTextColor(
  isDarkTheme: Boolean = isSystemInDarkTheme()
): Color = if (isDarkTheme) {
  SearchHighlightColorScheme.Dark.textColor
} else {

  SearchHighlightColorScheme.Light.textColor
}

@Composable
internal fun AppTheme(
  isDarkTheme: Boolean = isSystemInDarkTheme(),
  fontFamily: FontFamily? = jetbrainsMonoFontFamily(),
  content: @Composable () -> Unit,
) {

    val colorScheme = getColorScheme(isDarkTheme)

  MaterialTheme(
    colorScheme = colorScheme,
    typography = appTypography(fontFamily),
    content = content,
  )
}

private fun getColorScheme(darkTheme: Boolean) = when {
  darkTheme -> darkScheme
  else -> lightScheme
}
