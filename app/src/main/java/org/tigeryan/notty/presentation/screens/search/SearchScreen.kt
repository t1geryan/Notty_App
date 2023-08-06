package org.tigeryan.notty.presentation.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import org.tigeryan.notty.R
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.presentation.theme.spacing
import org.tigeryan.notty.presentation.views.ImageWithTextBelow
import org.tigeryan.notty.presentation.views.NoteItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchState,
    onSendIntent: (SearchIntent) -> Unit,
    onNavigateToNote: (Long) -> Unit,
    onNavigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            SearchTextFieldAppBar(
                value = state.input,
                onValueChange = {
                    onSendIntent(SearchIntent.UpdateInput(it))
                },
                onNavigateUp = onNavigateUp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium),
            )
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
                        modifier = Modifier
                            .align(Alignment.Center),
                    )
                } else if (isFailed) {
                    AlertDialog(
                        onDismissRequest = {},
                        title = {
                            Text(
                                text = stringResource(R.string.notes_loading_error),
                                style = MaterialTheme.typography.titleLarge,
                            )
                        },
                        text = {
                            exception?.message?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        },
                        confirmButton = {
                            Button(onClick = { onSendIntent(SearchIntent.GetNotesByInput(state.input)) }) {
                                Text(text = stringResource(R.string.try_again))
                            }
                        },
                    )
                } else {
                    if (notes.isEmpty()) {
                        ImageWithTextBelow(
                            painter = painterResource(id = R.mipmap.pic_notes_not_founded),
                            imageDescription = stringResource(R.string.no_notes_found_message),
                            text = stringResource(R.string.no_notes_found_message),
                            modifier = Modifier
                                .align(Alignment.Center),
                        )
                    } else {
                        NoteList(
                            notes = notes,
                            onNavigateToNote = onNavigateToNote,
                            modifier = Modifier
                                .padding(MaterialTheme.spacing.small)
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun NoteList(
    notes: List<Note>,
    onNavigateToNote: (Long) -> Unit,
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                NoteItem(
                    note = note,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onNavigateToNote(note.id)
                        },
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.spacing.small),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTextFieldAppBar(
    value: String,
    onValueChange: (String) -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface,
            contentColor = colorScheme.onSurface,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = {
                    Text(
                        text = stringResource(R.string.text_input_hint),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                leadingIcon = {
                    IconButton(
                        onClick = onNavigateUp,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_up_action_title),
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onValueChange("")
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = stringResource(id = R.string.navigate_up_action_title),
                        )
                    }
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorScheme.surface,
                    textColor = colorScheme.onSurface,
                    placeholderColor = colorScheme.onSurface.copy(alpha = 0.7f),
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(1f),
            )
        }
    }
}