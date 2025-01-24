// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared.ui.settings

import kotlinx.coroutines.flow.Flow

public interface Preference<T> {
  public val defaultValue: T
  public val flow: Flow<T>

  public suspend fun get(): T
  public suspend fun set(value: T)
}

public suspend fun Preference<Boolean>.toggle(): Unit = set(!get())
