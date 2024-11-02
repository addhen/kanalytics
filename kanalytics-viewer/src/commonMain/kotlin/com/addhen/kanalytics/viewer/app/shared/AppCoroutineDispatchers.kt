// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.shared

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

public data class AppCoroutineDispatchers(
  val io: CoroutineDispatcher = Dispatchers.IO,
  val default: CoroutineDispatcher = Dispatchers.Default,
  val databaseRead: CoroutineDispatcher = Dispatchers.IO.limitedParallelism(4),
  val databaseWrite: CoroutineDispatcher = Dispatchers.IO.limitedParallelism(1),
  val computation: CoroutineDispatcher = Dispatchers.Default,
  val main: CoroutineDispatcher = Dispatchers.Main,
)
