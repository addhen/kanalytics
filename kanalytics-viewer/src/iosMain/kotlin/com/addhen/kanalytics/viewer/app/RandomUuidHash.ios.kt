package com.addhen.kanalytics.viewer.app

import platform.Foundation.NSUUID

internal actual fun randomUuidHash(): Int = NSUUID().hash.toInt()
