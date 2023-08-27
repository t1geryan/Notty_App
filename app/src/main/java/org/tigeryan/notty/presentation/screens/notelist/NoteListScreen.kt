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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.tigeryan.notty.R
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.presentation.theme.spacing
import org.tigeryan.notty.presentation.views.Action
import org.tigeryan.notty.presentation.views.AppActionBar
import org.tigeryan.notty.presentation.views.ImageWithTextBelow
import org.tigeryan.notty.presentation.views.NoteItem
import org.tigeryan.notty.presentation.views.SwipeBackground
import org.tigeryan.notty.utils.extensions.rememberMutableStateOf

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

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()

    var selectedNote: Note? by rememberMutableStateOf(value = null)

    BottomSheetScaffold(
        topBar = {
            AppActionBar(
                title = stringResource(R.string.note_list_screen_title), actions = listOf(
                    Action(
                        title = stringResource(R.string.search_action_title),
                        icon = Icons.Outlined.Search,
                        onClick = onNavigateToSearch,
                    ), Action(
                        title = stringResource(R.string.settings_action_title),
                        icon = Icons.Outlined.Settings,
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
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.create_note_icon_description),
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        sheetContent = {
            BottomSheetElement(
                icon = Icons.Outlined.Share,
                label = stringResource(R.string.share_note_label),
                onClick = {
                    selectedNote?.let {
                        shareNote(context, it)
                    }
                },
            )
            BottomSheetElement(
                icon = Icons.Outlined.Delete,
                label = stringResource(R.string.delete_note_label),
                onClick = { /*TODO*/ },
            )
        },
        scaffoldState = scaffoldState,
        sheetGesturesEnabled = true,
        sheetPeekHeight = 0.dp,
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
                    AlertDialog(
                        onDismissRequest = {},
                        title = {
                            Text(
                                text = stringResource(R.string.notes_loading_error),
                                style = typography.titleLarge,
                            )
                        },
                        text = {
                            exception?.message?.let {
                                Text(
                                    text = it,
                                    style = typography.bodyLarge,
                                )
                            }
                        },
                        confirmButton = {
                            Button(onClick = { onSendIntent(NoteListIntent.GetAllNotesIntent) }) {
                                Text(text = stringResource(R.string.try_again))
                            }
                        },
                    )
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
                            onClick = {
                                onNavigateToNote(notes[it].note.id)
                            },
                            onLongClick = {
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                    selectedNote = notes[it].note
                                }
                            },
                            onSendIntent = onSendIntent,
                            modifier = Modifier.padding(MaterialTheme.spacing.small)
                        )
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
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun NoteList(
    notes: List<NoteItem>,
    onLongClick: (Int) -> Unit,
    onClick: (Int) -> Unit,
    onSendIntent: (NoteListIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(items = notes, key = { _, noteItem ->
            noteItem.note.id
        }) { index, noteItem ->
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
                                icon = Icons.Default.Delete,
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
                                        onClick(index)
                                    },
                                    onLongClick = {
                                        onLongClick(index)
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