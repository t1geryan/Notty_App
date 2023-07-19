package org.tigeryan.notty.presentation.screens.notelist

import androidx.compose.runtime.Composable

@Composable
fun NoteListScreen(
    state: NoteListState,
    onSendIntent: (NoteListIntent) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToNote: (Long) -> Unit,
) {

}