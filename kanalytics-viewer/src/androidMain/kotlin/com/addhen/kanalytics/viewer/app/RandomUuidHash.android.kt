package com.addhen.kanalytics.viewer.app

internal actual fun randomUuidHash(): Int = java.util.UUID.randomUUID().hashCode()
