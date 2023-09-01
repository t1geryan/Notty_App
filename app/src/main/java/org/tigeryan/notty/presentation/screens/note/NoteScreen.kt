package org.tigeryan.notty.presentation.screens.note

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import org.tigeryan.notty.R
import org.tigeryan.notty.presentation.theme.icons
import org.tigeryan.notty.presentation.views.Action
import org.tigeryan.notty.presentation.views.AppActionBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NoteScreen(
    state: NoteState,
    onSendIntent: (NoteIntent) -> Unit,
    eventsFlow: Flow<NoteViewModel.Event>,
    onNavigateUp: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppActionBar(
                title = null,
                navigationAction = Action.navigateUpAction(
                    title = stringResource(id = R.string.navigate_up_action_title),
                    onClick = {
                        onNavigateUp()
                    },
                ),
                actions = listOf(
                    Action(
                        title = stringResource(R.string.delete_note_action_title),
                        icon = MaterialTheme.icons.delete,
                        onClick = {
                            onSendIntent(NoteIntent.DeleteNoteIntent)
                        },
                    )
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                val (focusRequester) = FocusRequester.createRefs()

                TextField(
                    value = state.title,
                    onValueChange = {
                        onSendIntent(NoteIntent.UpdateTitleIntent(it))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.title_input_hint),
                            style = typography.titleLarge,
                        )
                    },
                    colors = TextFieldDefaults.noteTextFieldColors(),
                    textStyle = typography.titleLarge,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester.requestFocus() }
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                TextField(
                    value = state.text,
                    onValueChange = {
                        onSendIntent(NoteIntent.UpdateTextIntent(it))
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.text_input_hint),
                            style = typography.bodyLarge,
                        )
                    },
                    colors = TextFieldDefaults.noteTextFieldColors(),
                    textStyle = typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxSize()
                        .focusRequester(focusRequester),
                )
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        eventsFlow.collectLatest { event ->
            when (event) {
                is NoteViewModel.Event.NavigateUp -> onNavigateUp()
                is NoteViewModel.Event.ShowDeletionConfirmation -> {
                    val snackbarResult = snackbarHostState.showSnackbar(
                        message = context.getString(R.string.note_deletion_confirmation_message),
                        actionLabel = context.getString(R.string.delete_note_action),
                    )
                    if (snackbarResult == SnackbarResult.ActionPerformed)
                        event.onConfirm()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextFieldDefaults.noteTextFieldColors() = textFieldColors(
    containerColor = colorScheme.background,
    textColor = colorScheme.onBackground,
    placeholderColor = colorScheme.onBackground.copy(alpha = 0.7f),
    focusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
)
