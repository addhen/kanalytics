package com.addhen.kanalytics.viewer.app.shared.ui.component

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.addhen.kanalytics.viewer.app.randomUuidHash
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Copied from https://github.com/DroidKaigi/conference-app-2023 codebase with a slight modification
@Composable
internal fun SnackbarMessageEffect(
  actionLabel: String? = null,
  snackbarHostState: SnackbarHostState,
  uiMessageStateHolder: UiMessageStateHolder,
) {
  uiMessageStateHolder.uiMessageState.uiMessages.firstOrNull()?.let { uiMessage ->
    LaunchedEffect(uiMessage) {
      val snackbarResult: SnackbarResult = if (uiMessage.duration != null) {
        snackbarHostState.showSnackbar(
          message = uiMessage.message,
          duration = uiMessage.duration,
          actionLabel = actionLabel,
        )
      } else {
        snackbarHostState.showSnackbar(
          message = uiMessage.message,
          actionLabel = actionLabel,
        )
      }
      uiMessageStateHolder.messageShown(
        messageId = uiMessage.id,
        uiMessageAction = when (snackbarResult) {
          SnackbarResult.Dismissed -> {
            UiMessageAction.Dismissed
          }

          SnackbarResult.ActionPerformed -> {
            UiMessageAction.ActionPerformed
          }
        },
      )
    }
  }
}

internal class UiMessageManager : UiMessageStateHolder {
  private var _uiMessagesState by mutableStateOf(UiMessageState())

  override val uiMessageState: UiMessageState get() = _uiMessagesState

  override fun messageShown(messageId: Long, uiMessageAction: UiMessageAction) {
    val messages = _uiMessagesState.uiMessages.toMutableList()
    messages.indexOfFirst { it.id == messageId }.let { uiMessageIndex ->
      if (uiMessageIndex == -1) return@let
      messages[uiMessageIndex] = messages[uiMessageIndex].copy(action = uiMessageAction)
    }
    _uiMessagesState = _uiMessagesState.copy(uiMessages = messages)
  }

  override suspend fun showMessage(message: UiMessage): UiMessageAction {
    val messages = uiMessageState.uiMessages.toMutableList()
    messages.add(message)
    _uiMessagesState = _uiMessagesState.copy(uiMessages = messages)
    val action = snapshotFlow { _uiMessagesState }.filter { messageState ->
      messageState.uiMessages.find { it.id == message.id }?.let { uiMessage ->
        val action = uiMessage.action
        action != null
      } ?: false
    }.map { messageState ->
      val uiMessage = checkNotNull(messageState.uiMessages.find { it.id == message.id })
      checkNotNull(uiMessage.action)
    }.first()

    val newMessages = _uiMessagesState.uiMessages.toMutableList()
    newMessages.find { it.id == message.id }?.let { uiMessage ->
      newMessages.remove(uiMessage)
    }
    _uiMessagesState = _uiMessagesState.copy(uiMessages = newMessages)
    return action
  }

  internal companion object {
    val Instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
      UiMessageManager()
    }
  }
}

internal data class UiMessage(
  val message: String,
  val duration: SnackbarDuration? = null,
  val id: Long = randomUuidHash().toLong(),
  val action: UiMessageAction? = null,
)

internal data class UiMessageState(val uiMessages: List<UiMessage> = emptyList())

internal interface UiMessageStateHolder {
  val uiMessageState: UiMessageState
  suspend fun showMessage(message: UiMessage): UiMessageAction
  fun messageShown(messageId: Long, uiMessageAction: UiMessageAction)
}

internal enum class UiMessageAction {
  Dismissed,
  ActionPerformed,
}
