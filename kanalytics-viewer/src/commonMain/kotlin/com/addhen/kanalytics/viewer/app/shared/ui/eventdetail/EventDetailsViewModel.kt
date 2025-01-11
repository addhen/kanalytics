package com.addhen.kanalytics.viewer.app.shared.ui.eventdetail

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.addhen.kanalytics.handleErrorWithRetry
import com.addhen.kanalytics.stateInWhileSubscribed
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.data.repository.EventDataRepository
import com.addhen.kanalytics.viewer.app.shared.ui.component.UiMessageManager
import com.addhen.kanalytics.viewer.app.shared.ui.component.UiMessageStateHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
internal class EventDetailsViewModel(
  private val eventRepository: EventDataRepository,
  internal val uiMessageStateHolder: UiMessageStateHolder
) : ViewModel(), UiMessageStateHolder by uiMessageStateHolder {

  private val viewStateEmitter = MutableStateFlow(
    EventDetailsUiState(flag = EventDetailsUiState.Flag.LOADING)
  )
  private val uiAction = MutableSharedFlow<UiAction>()

  val viewState: StateFlow<EventDetailsUiState> = viewStateEmitter
    .stateInWhileSubscribed(viewModelScope, viewStateEmitter.value)

  val action: (UiAction) -> Unit = { action ->
    viewModelScope.launch { uiAction.emit(action) }
  }

  init {
    uiAction
      .flatMapLatest { action ->
        when (action) {
          is UiAction.LoadEventDetails -> {
            eventRepository.getEventById(action.eventId)
              .handleErrorWithRetry(uiMessageStateHolder)
          }
        }
      }
      .onEach {event ->
        viewStateEmitter.update { currentUiState ->
          currentUiState.copy(
            flag = EventDetailsUiState.Flag.IDLE,
            event = event
          )
        }
      }
      .launchIn(viewModelScope)
  }

  @Stable
  data class EventDetailsUiState(
    val event: EventData? = null,
    val flag: Flag = Flag.IDLE,
  ) {
    enum class Flag {
      LOADING,
      IDLE,
    }
  }

  sealed interface UiAction {
    data class LoadEventDetails(val eventId: Long) : UiAction
  }

  companion object {

    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        val repository = EventDataRepository.Instance
        val uiMessageStateHolder = UiMessageManager()
        EventDetailsViewModel(
          eventRepository = repository,
          uiMessageStateHolder = uiMessageStateHolder
        )
      }
    }
  }
}
