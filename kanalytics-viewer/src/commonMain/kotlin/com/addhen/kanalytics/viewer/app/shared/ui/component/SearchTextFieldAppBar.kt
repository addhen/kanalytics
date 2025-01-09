// Copyright 2023, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.Res
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.search_icon_content_description
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.search_term_placeHolder
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchTextFieldAppBar(
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit,
  testTag: String,
  modifier: Modifier = Modifier,
) {
  TopAppBar(
    modifier = modifier,
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.background,
    ),
    title = {
      SearchTextField(
        searchQuery = searchQuery,
        onSearchQueryChanged = onSearchQueryChanged,
        modifier = Modifier
          .testTag(testTag),
      )
    },
  )
}

@Composable
private fun SearchTextField(
  modifier: Modifier = Modifier,
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit = {},
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  val focusRequester = remember { FocusRequester() }
  val keyboardController = LocalSoftwareKeyboardController.current
  var query by remember { mutableStateOf(searchQuery) }

  OutlinedTextField(
    value = query,
    onValueChange = { value: String ->
      query = value
      onSearchQueryChanged(value)
    },
    placeholder = { Text(stringResource(Res.string.search_term_placeHolder)) },
    modifier = modifier
      .fillMaxWidth()
      .padding(end = 8.dp)
      .focusRequester(focusRequester),
    enabled = enabled,
    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
    singleLine = true,
    leadingIcon = {
      Icon(
        Icons.Default.Search,
        contentDescription = stringResource(Res.string.search_icon_content_description),
      )
    },
    trailingIcon = {
      if (query.isNotEmpty()) {
        Box(modifier = Modifier.offset(x = (-4).dp)) {
          IconButton(
            onClick = {
              query = ""
              onSearchQueryChanged("")
              // This is mostly for iOS, otherwise there is no way to dismiss the iOS
              // keyboard once opened.
              keyboardController?.hide()
            },
          ) {
            Icon(
              imageVector = Icons.Default.Clear,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onSurface,
            )
          }
        }
      }
    },
    keyboardOptions = KeyboardOptions.Default,
    keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
    interactionSource = interactionSource,
  )
}
