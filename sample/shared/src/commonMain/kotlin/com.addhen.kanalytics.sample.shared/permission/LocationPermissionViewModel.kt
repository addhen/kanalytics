// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.sample.shared.permission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

public class LocationPermissionViewModel(
  public val permissionsController: PermissionsController,
  private vararg val permissionTypes: Permission,
) : ViewModel() {

  private val viewStateEmitter =
    MutableStateFlow(
      LocationPermissionUiState(flag = LocationPermissionUiState.Flag.CHECK_PERMISSION),
    )

  public val viewState: StateFlow<LocationPermissionUiState> = viewStateEmitter
    .stateIn(
      viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      viewStateEmitter.value,
    )

  public fun onRequestPermissionButtonPressed() {
    requestPermission(permissions = permissionTypes)
  }

  private fun requestPermission(vararg permissions: Permission) {
    viewModelScope.launch {
      try {
        for (permission in permissions) {
          permissionsController.providePermission(permission)
        }

        viewStateEmitter.update { currentLocationPermissionUiState ->
          currentLocationPermissionUiState.copy(
            flag = LocationPermissionUiState.Flag.PERMISSION_GRANTED,
          )
        }
      } catch (deniedAlways: DeniedAlwaysException) {
        // Permission is always denied.
        viewStateEmitter.update { currentLocationPermissionUiState ->
          currentLocationPermissionUiState.copy(
            flag = LocationPermissionUiState.Flag.PERMISSION_DENIED_ALWAYS,
          )
        }
      } catch (denied: DeniedException) {
        // Permission was denied.
        viewStateEmitter.update { currentLocationPermissionUiState ->
          currentLocationPermissionUiState.copy(
            flag = LocationPermissionUiState.Flag.PERMISSION_DENIED,
          )
        }
      } catch (cause: Throwable) {
        ensureActive()
        viewStateEmitter.update { currentLocationPermissionUiState ->
          currentLocationPermissionUiState.copy(
            flag = LocationPermissionUiState.Flag.ERROR,
          )
        }
      }
    }
  }

  public data class LocationPermissionUiState(
    public val flag: Flag = Flag.CHECK_PERMISSION,
  ) {
    public enum class Flag {
      CHECK_PERMISSION,
      PERMISSION_GRANTED,
      PERMISSION_DENIED,
      PERMISSION_DENIED_ALWAYS,
      ERROR,
    }
  }
}
