// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.Res
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.back_icon_content_description
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.settings_data_retention_summary
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.settings_data_retention_title
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.settings_dynamic_color_summary
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.settings_dynamic_color_title
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.settings_mode_title
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.settings_title
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.settings_ui_theme_category_title
import com.addhen.kanalytics.viewer.app.shared.ui.ViewerAppScaffold
import com.addhen.kanalytics.viewer.app.shared.ui.component.CheckboxPreference
import com.addhen.kanalytics.viewer.app.shared.ui.component.DropdownPreference
import com.addhen.kanalytics.viewer.app.shared.ui.component.Preference
import com.addhen.kanalytics.viewer.app.shared.ui.component.PreferenceDivider
import com.addhen.kanalytics.viewer.app.shared.ui.component.PreferenceHeader
import com.addhen.kanalytics.viewer.app.shared.ui.component.collectAsState
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun PreferencesScreen(onBack: () -> Unit) {
  val viewModel: PreferencesViewModel = viewModel(factory = PreferencesViewModel.Factory)
  val useDynamicColors by viewModel.defaultPreferences.useDynamicColors.collectAsState()
  val dataRetentionDays by viewModel.defaultPreferences.dataRetentionDays.collectAsState()
  val theme by viewModel.defaultPreferences.theme.collectAsState()

  PreferencesContent(
    theme = theme,
    useDynamicColors = useDynamicColors,
    selectedDay = dataRetentionDays,
    snackbarHostState = remember { SnackbarHostState() },
    onThemeSelected = { viewModel.action(PreferencesViewModel.UiAction.SaveTheme(it)) },
    onDynamicColorSelected = { viewModel.action(PreferencesViewModel.UiAction.UseDynamicColors) },
    onDaySelected = { viewModel.action(PreferencesViewModel.UiAction.SelectDays(it)) },
    onBack = onBack,
  )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PreferencesContent(
  theme: ViewerAppPreferences.Theme,
  useDynamicColors: Boolean,
  selectedDay: Int,
  snackbarHostState: SnackbarHostState,
  onThemeSelected: (ViewerAppPreferences.Theme) -> Unit,
  onDaySelected: (Int) -> Unit,
  onDynamicColorSelected: () -> Unit,
  onBack: () -> Unit,
) {

  ViewerAppScaffold(
    title = stringResource(Res.string.settings_title),
    snackbarHostState = snackbarHostState,
    navigationIcon = {
      IconButton(onClick = onBack) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = stringResource(Res.string.back_icon_content_description),
        )
      }
    },
  ) {
    LazyColumn(
      modifier = Modifier.fillMaxWidth(),
    ) {
      stickyHeader {
        PreferenceHeader(stringResource(Res.string.settings_ui_theme_category_title))
      }

      item {
        ThemePreference(
          title = stringResource(Res.string.settings_mode_title),
          selected = theme,
          onThemeSelected = onThemeSelected,
        )
      }

      item { PreferenceDivider() }

      item {
        CheckboxPreference(
          title = stringResource(Res.string.settings_dynamic_color_title),
          summaryOff = stringResource(Res.string.settings_dynamic_color_summary),
          onCheckClicked = onDynamicColorSelected,
          checked = useDynamicColors,
        )
      }

      item { PreferenceDivider() }

      item {
        DropdownPreference(
          selectedDay = selectedDay,
          onDaySelected = onDaySelected,
          title = stringResource(Res.string.settings_data_retention_title),
          summary = stringResource(Res.string.settings_data_retention_summary),
        )
      }
      item { PreferenceDivider() }
    }
  }
}

@Composable
private fun ThemePreference(
  selected: ViewerAppPreferences.Theme,
  onThemeSelected: (ViewerAppPreferences.Theme) -> Unit,
  title: String,
  modifier: Modifier = Modifier,
) {

  Preference(
    title = title,
    control = {
      Row(Modifier.selectableGroup()) {
        ThemeButton(
          icon = Icons.Default.LightMode,
          onClick = { onThemeSelected(ViewerAppPreferences.Theme.LIGHT) },
          isSelected = selected == ViewerAppPreferences.Theme.LIGHT,
        )

        ThemeButton(
          icon = Icons.Default.DarkMode,
          onClick = { onThemeSelected(ViewerAppPreferences.Theme.DARK) },
          isSelected = selected == ViewerAppPreferences.Theme.DARK,
        )
        ThemeButton(
          icon = Icons.Default.AutoMode,
          onClick = { onThemeSelected(ViewerAppPreferences.Theme.SYSTEM) },
          isSelected = selected == ViewerAppPreferences.Theme.SYSTEM,
        )
      }
    },
    modifier = modifier,
  )
}

@Composable
private fun ThemeButton(isSelected: Boolean, icon: ImageVector, onClick: () -> Unit) {
  FilledIconToggleButton(
    checked = isSelected,
    onCheckedChange = { onClick() },
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
    )
  }
}
