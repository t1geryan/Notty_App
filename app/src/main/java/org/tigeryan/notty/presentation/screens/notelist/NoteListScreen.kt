package org.tigeryan.notty.presentation.screens.notelist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import org.tigeryan.notty.R
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.presentation.theme.dimens
import org.tigeryan.notty.presentation.theme.spacing
import org.tigeryan.notty.presentation.views.Action
import org.tigeryan.notty.presentation.views.AppActionBar
import org.tigeryan.notty.presentation.views.ImageWithTextBelow
import org.tigeryan.notty.presentation.views.SwipeBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    state: NoteListState,
    onSendIntent: (NoteListIntent) -> Unit,
    onNavigateToEditor: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToNote: (Long) -> Unit,
) {
    Scaffold(
        topBar = {
            AppActionBar(
                title = stringResource(R.string.note_list_screen_title),
                actions = listOf(
                    Action(
                        title = stringResource(R.string.search_action_title),
                        icon = Icons.Outlined.Search,
                        onClick = onNavigateToSearch,
                    ),
                    Action(
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
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            with(state) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center),
                    )
                } else if (isFailed) {
                    // TODO: add failure block in NoteListScreen
                } else {
                    if (notes.isEmpty()) {
                        ImageWithTextBelow(
                            painter = painterResource(id = R.mipmap.pic_no_notes),
                            imageDescription = stringResource(R.string.no_notes_message),
                            text = stringResource(R.string.no_notes_message),
                            modifier = Modifier
                                .align(Alignment.Center),
                        )
                    } else {
                        NoteList(
                            notes = notes,
                            onNavigateToNote = onNavigateToNote,
                            onSendIntent = onSendIntent,
                            modifier = Modifier
                                .padding(MaterialTheme.spacing.small)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun NoteList(
    notes: List<Note>,
    onNavigateToNote: (Long) -> Unit,
    onSendIntent: (NoteListIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = notes,
            key = { note ->
                note.id
            }
        ) { note ->
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
                modifier = Modifier
                    .fillMaxWidth()
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
                            modifier = Modifier
                                .fillMaxSize()
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
                            .clickable {
                                onNavigateToNote(note.id)
                            },
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

@Composable
private fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.primaryContainer,
            contentColor = colorScheme.onPrimaryContainer,
            disabledContainerColor = colorScheme.primaryContainer,
            disabledContentColor = colorScheme.onPrimaryContainer,
        ),
        shape = shapes.large,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
        ) {
            Text(
                text = note.title,
                style = typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = MaterialTheme.dimens.singleTextLinesCount,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = note.text,
                style = typography.bodyLarge,
                maxLines = MaterialTheme.dimens.smallTextLinesCount,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}