// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.shared.permission

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.addhen.kanalytics.sample.shared.ScaffoldSample
import com.addhen.kanalytics.sample.shared.navigation.LocationRoute
import com.addhen.kanalytics.sample.shared.navigation.buildNavOptions
import com.addhen.kanalytics.shared.generated.resources.Res
import com.addhen.kanalytics.shared.generated.resources.ic_launcher_foreground
import com.addhen.kanalytics.shared.generated.resources.request_location_permission_rationale
import com.addhen.kanalytics.shared.generated.resources.request_permission
import dev.icerock.moko.permissions.compose.BindEffect
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
public fun LocationPermissionScreen(
  viewModel: LocationPermissionViewModel,
  navController: NavController,
) {
  val coroutineScope = rememberCoroutineScope()
  BindEffect(viewModel.permissionsController)
  val permissionUiState by viewModel.viewState.collectAsState()
  ScaffoldSample(title = stringResource(Res.string.request_permission)) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      when (permissionUiState.flag) {
        LocationPermissionViewModel.LocationPermissionUiState.Flag.PERMISSION_GRANTED -> {
          navController.navigate(LocationRoute, navController.buildNavOptions())
        }
        LocationPermissionViewModel.LocationPermissionUiState.Flag.PERMISSION_DENIED,
        LocationPermissionViewModel.LocationPermissionUiState.Flag.PERMISSION_DENIED_ALWAYS,
        -> {
          coroutineScope.launch {
            viewModel.permissionsController.openAppSettings()
          }
        }

        LocationPermissionViewModel.LocationPermissionUiState.Flag.CHECK_PERMISSION -> {
          RequestPermission(
            promptText = Res.string.request_location_permission_rationale,
            buttonText = Res.string.request_permission,
            onButtonClick = { viewModel.onRequestPermissionButtonPressed() },
          )
        }
        LocationPermissionViewModel.LocationPermissionUiState.Flag.ERROR -> {
          RequestPermission(
            promptText = Res.string.request_location_permission_rationale,
            buttonText = Res.string.request_permission,
            onButtonClick = { viewModel.onRequestPermissionButtonPressed() },
          )
        }
      }
    }
  }
}

@Composable
public fun RequestPermission(
  promptText: StringResource,
  buttonText: StringResource,
  onButtonClick: () -> Unit,
) {
  ImageResource(modifier = Modifier.size(80.dp, 80.dp), image = Res.drawable.ic_launcher_foreground)
  Spacer(modifier = Modifier.height(16.dp))
  Text(text = stringResource(promptText), textAlign = TextAlign.Center)
  Spacer(modifier = Modifier.height(16.dp))
  Button(onClick = { onButtonClick() }) {
    Text(text = stringResource(buttonText))
  }
}

@Composable
private fun ImageResource(
  modifier: Modifier,
  image: DrawableResource,
  imageDescription: String? = null,
) {
  Image(
    modifier = modifier,
    painter = painterResource(image),
    contentDescription = imageDescription,
  )
}
