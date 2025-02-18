// Copyright 2024, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app.android

import android.content.Context
import androidx.startup.Initializer

internal class ContextInitializer : Initializer<Context> {

  override fun create(context: Context): Context {
    _applicationContext = context
    return context
  }

  override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()

  companion object {
    private var _applicationContext: Context? = null
    val applicationContext: Context get() = _applicationContext
      ?: throw IllegalStateException("Context not initialized")
  }
}
