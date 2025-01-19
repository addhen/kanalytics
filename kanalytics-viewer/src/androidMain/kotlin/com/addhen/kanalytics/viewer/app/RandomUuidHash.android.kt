// Copyright 2025, Addhen Ltd and the kanalytics project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.kanalytics.viewer.app

internal actual fun randomUuidHash(): Int = java.util.UUID.randomUUID().hashCode()
