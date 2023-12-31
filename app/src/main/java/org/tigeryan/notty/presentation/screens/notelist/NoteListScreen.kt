package org.tigeryan.notty.presentation.screens.notelist

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.tigeryan.notty.R
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.presentation.theme.icons
import org.tigeryan.notty.presentation.theme.spacing
import org.tigeryan.notty.presentation.views.Action
import org.tigeryan.notty.presentation.views.AppActionBar
import org.tigeryan.notty.presentation.views.DialogCallbackTypes
import org.tigeryan.notty.presentation.views.ImageWithTextBelow
import org.tigeryan.notty.presentation.views.NoteItem
import org.tigeryan.notty.presentation.views.NottyDialog
import org.tigeryan.notty.presentation.views.SwipeBackground

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun NoteListScreen(
    state: NoteListState,
    onSendIntent: (NoteListIntent) -> Unit,
    eventsFlow: Flow<NoteListViewModel.Event>,
    onNavigateToEditor: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToNote: (Long) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetContent = {
            state.selectedNote?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = it.noteData.title,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(0.5f),
                    )
                }
            }
            BottomSheetElement(
                icon = MaterialTheme.icons.edit,
                label = stringResource(R.string.edit_note_label),
                onClick = {
                    state.selectedNote?.let {
                        onNavigateToNote(it.id)
                    }
                }
            )
            BottomSheetElement(
                icon = MaterialTheme.icons.share,
                label = stringResource(R.string.share_note_label),
                onClick = {
                    state.selectedNote?.let {
                        shareNote(context, it)
                    }
                },
            )
            BottomSheetElement(
                icon = MaterialTheme.icons.delete,
                label = stringResource(R.string.delete_note_label),
                onClick = {
                    state.selectedNote?.let {
                        onSendIntent(NoteListIntent.DeleteNoteIntent(it))
                    }
                    scope.launch {
                        sheetState.hide()
                    }
                },
            )
        },
        sheetState = sheetState,
        sheetShape = shapes.extraLarge.copy(bottomEnd = CornerSize(0), bottomStart = CornerSize(0)),
        sheetBackgroundColor = colorScheme.background,
        sheetContentColor = colorScheme.onBackground,
    ) {
        Scaffold(
            topBar = {
                AppActionBar(
                    title = stringResource(R.string.note_list_screen_title), actions = listOf(
                        Action(
                            title = stringResource(R.string.search_action_title),
                            icon = MaterialTheme.icons.search,
                            onClick = onNavigateToSearch,
                        ), Action(
                            title = stringResource(R.string.settings_action_title),
                            icon = MaterialTheme.icons.settings,
                            onClick = onNavigateToSettings
                        )
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onNavigateToEditor,
                    containerColor = colorScheme.surface,
                    contentColor = colorScheme.onSurface,
                    shape = shapes.extraLarge,
                ) {
                    Icon(
                        imageVector = MaterialTheme.icons.add,
                        contentDescription = stringResource(R.string.create_note_icon_description),
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                with(state) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                        )
                    } else if (isFailed) {
                        NottyDialog(
                            title = stringResource(R.string.notes_loading_error),
                            message = exception?.message
                                ?: stringResource(id = R.string.unexpected_error_message),
                            confirmButtonText = stringResource(R.string.try_again),
                            isDismissible = false,
                        ) { what ->
                            if (what == DialogCallbackTypes.CONFIRM) {
                                onSendIntent(NoteListIntent.GetAllNotesIntent)
                            }
                        }
                    } else {
                        if (notes.isEmpty()) {
                            ImageWithTextBelow(
                                painter = painterResource(id = R.mipmap.pic_no_notes),
                                imageDescription = stringResource(R.string.no_notes_message),
                                text = stringResource(R.string.no_notes_message),
                                modifier = Modifier.align(Alignment.Center),
                            )
                        } else {
                            NoteList(
                                notes = notes,
                                onClick = { note ->
                                    onNavigateToNote(note.id)
                                },
                                onLongClick = { note ->
                                    onSendIntent(NoteListIntent.SelectNoteIntent(note))
                                },
                                onSendIntent = onSendIntent,
                                modifier = Modifier.padding(MaterialTheme.spacing.small)
                            )
                        }
                    }
                }
            }
        }

        LaunchedEffect(key1 = Unit) {
            eventsFlow.collectLatest { event ->
                when (event) {
                    is NoteListViewModel.Event.ShowSnackbar -> {
                        val result = snackbarHostState.showSnackbar(
                            message = context.getString(event.message),
                            actionLabel = context.getString(event.actionTitle),
                        )
                        when (result) {
                            SnackbarResult.Dismissed -> event.onDismiss()
                            SnackbarResult.ActionPerformed -> event.onAction()
                        }
                    }

                    is NoteListViewModel.Event.ShowBottomSheetForNote -> {
                        sheetState.show()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun NoteList(
    notes: List<NoteItem>,
    onLongClick: (Note) -> Unit,
    onClick: (Note) -> Unit,
    onSendIntent: (NoteListIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = notes,
            key = { noteItem ->
                noteItem.note.id
            }
        ) { noteItem ->
            val note = noteItem.note
            if (noteItem.isVisible) {
                val dismissState = rememberDismissState {
                    when (it) {
                        DismissValue.DismissedToStart -> {
                            onSendIntent(NoteListIntent.DeleteNoteIntent(note))
                            true
                        }

                        else -> false
                    }
                }

                val directions = setOf(DismissDirection.EndToStart)
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SwipeToDismiss(
                        directions = directions,
                        state = dismissState,
                        dismissThresholds = {
                            FractionalThreshold(0.75f)
                        },
                        background = {
                            SwipeBackground(
                                dismissState = dismissState,
                                icon = MaterialTheme.icons.delete,
                                directions = directions,
                                shape = shapes.large,
                                containerColor = colorScheme.secondary,
                                contentColor = colorScheme.onSecondary,
                                modifier = Modifier.fillMaxSize()
                            )
                        },
                        modifier = Modifier
                            .padding(
                                horizontal = MaterialTheme.spacing.small,
                                vertical = MaterialTheme.spacing.tiny
                            )
                            .animateItemPlacement(),
                    ) {
                        NoteItem(
                            note = note,
                            modifier = Modifier
                                .fillMaxWidth()
                                .combinedClickable(
                                    onClick = {
                                        onClick(note)
                                    },
                                    onLongClick = {
                                        onLongClick(note)
                                    },
                                ),
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MaterialTheme.spacing.small),
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomSheetElement(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconTint: Color = colorScheme.primary,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(
                horizontal = MaterialTheme.spacing.large,
                vertical = MaterialTheme.spacing.medium,
            ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
        )
        Spacer(
            modifier = Modifier
                .width(MaterialTheme.spacing.large)
        )
        Text(
            text = label,
        )
    }
}

private fun shareNote(context: Context, note: Note) {
    val (title, text) = note.noteData
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "$title\n$text")
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}