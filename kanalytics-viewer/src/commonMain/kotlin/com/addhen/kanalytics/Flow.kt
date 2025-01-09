package com.addhen.kanalytics

import com.addhen.kanalytics.viewer.app.shared.ui.component.UiMessage
import com.addhen.kanalytics.viewer.app.shared.ui.component.UiMessageAction
import com.addhen.kanalytics.viewer.app.shared.ui.component.UiMessageStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

internal fun <T> Flow<T>.stateInWhileSubscribed(
  scope: CoroutineScope,
  initialValue: T
): StateFlow<T> {
  return stateIn(
    scope = scope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = initialValue,
  )
}

internal fun <T> Flow<T>.handleErrorWithRetry(
  uiMessageStateHolder: UiMessageStateHolder
): Flow<T> = retry { throwable ->
  throwable.printStackTrace()
  val action = uiMessageStateHolder.showMessage(
    UiMessage(
      message = throwable.message ?: "An error occurred",
    )
  )
  action == UiMessageAction.ActionPerformed
}.catch { /* Do nothing if the user dose not retry. */ }
