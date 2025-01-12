package com.addhen.kanalytics.viewer.app.shared.ui.eventdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.Res
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.back_icon_content_description
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.created_at
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.description
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.event_details_title
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.id
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.name
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.no_events_found
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.retry
import com.addhen.kanalytics.kanalytics_viewer.generated.resources.tracker_name
import com.addhen.kanalytics.viewer.app.shared.data.model.EventData
import com.addhen.kanalytics.viewer.app.shared.data.toPrettyPrintJsonString
import com.addhen.kanalytics.viewer.app.shared.ui.ViewerAppScaffold
import com.addhen.kanalytics.viewer.app.shared.ui.component.EmptyContent
import com.addhen.kanalytics.viewer.app.shared.ui.component.LoadingText
import com.addhen.kanalytics.viewer.app.shared.ui.component.SnackbarMessageEffect
import com.addhen.kanalytics.viewer.app.shared.ui.toStyledKeyValueString
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun EventDetailsScreen(
  eventId: Long,
  eventName: String,
  onBack: () -> Unit
) {
  val viewModel: EventDetailsViewModel = viewModel(factory = EventDetailsViewModel.create(eventId))
  val uiState by viewModel.viewState.collectAsStateWithLifecycle()
  val snackbarHostState = remember { SnackbarHostState() }

  val retryMessageText = stringResource(Res.string.retry)
  SnackbarMessageEffect(
    snackbarHostState = snackbarHostState,
    actionLabel = retryMessageText,
    uiMessageStateHolder = viewModel.uiMessageStateHolder,
  )

  EventDetailsScreen(
    eventName = eventName,
    snackbarHostState = snackbarHostState,
    uiState = uiState,
    onBack = onBack
  )
}

@Composable
internal fun EventDetailsScreen(
  eventName: String,
  snackbarHostState: SnackbarHostState,
  uiState: EventDetailsViewModel.EventDetailsUiState,
  onBack: () -> Unit
) {

  ViewerAppScaffold(
    title = stringResource(Res.string.event_details_title, eventName),
    snackbarHostState = snackbarHostState,
    navigationIcon = {
      IconButton(onClick = onBack) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = stringResource(Res.string.back_icon_content_description)
        )
      }
    }
  ) {
      EventDetailsContent(
        uiState = uiState,
        listState = rememberLazyListState(),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize()
      )
  }
}

@Composable
private fun EventDetailsContent(
  uiState: EventDetailsViewModel.EventDetailsUiState,
  listState: LazyListState,
  contentPadding: PaddingValues,
  modifier: Modifier = Modifier,
) {
  Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
   AnimatedVisibility(
      modifier = Modifier.fillMaxSize(),
      visible = (uiState.flag == EventDetailsViewModel.EventDetailsUiState.Flag.LOADING),
      enter = fadeIn(),
      exit = fadeOut(),
    ) {
      LoadingText(
        modifier = Modifier
          .fillMaxSize()
          .background(MaterialTheme.colorScheme.background),
      )
    }
    when (uiState.flag) {
      EventDetailsViewModel.EventDetailsUiState.Flag.LOADING -> {
        // Do nothing as loading view is already shown above
      }

      EventDetailsViewModel.EventDetailsUiState.Flag.IDLE -> {
        if (uiState.event == null) {
          EmptyContent(
            title = { Text(stringResource(Res.string.no_events_found)) },
            modifier = Modifier.fillMaxWidth(),
          )
        } else {
          LazyColumn(
            state = listState,
            modifier = modifier,
            contentPadding = contentPadding,
          ) {
            item {
              Column(modifier = Modifier.padding(8.dp)) {
                Text(text = toAnnotatedEventDescription(uiState.event))
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = uiState.event.properties.toPrettyPrintJsonString())
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun toAnnotatedEventDescription(eventData: EventData) = buildAnnotatedString {
  toStyledKeyValueString(stringResource(Res.string.id), eventData.id.toString())
  toStyledKeyValueString(stringResource(Res.string.name), eventData.name)
  toStyledKeyValueString(stringResource(Res.string.created_at), eventData.createdAt.toString())
  toStyledKeyValueString(stringResource(Res.string.tracker_name), eventData.trackerName)
  if (eventData.description.isNullOrBlank().not()) {
    toStyledKeyValueString(stringResource(Res.string.description), eventData.description)
  }
}
