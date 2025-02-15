// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings
import platform.Foundation.NSUserDefaults

internal actual fun provideObservableSettings(): ObservableSettings =
  NSUserDefaultsSettings(delegate = NSUserDefaults.standardUserDefaults)
