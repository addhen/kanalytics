// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app

import platform.Foundation.NSUUID

internal actual fun randomUuidHash(): Int = NSUUID().hash.toInt()
