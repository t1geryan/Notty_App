package org.tigeryan.notty.presentation.screens.note

import org.tigeryan.mvi.Intent

sealed interface NoteIntent : Intent {

    data class UpdateTitleIntent(val title: String) : NoteIntent

    data class UpdateTextIntent(val text: String) : NoteIntent

    object DeleteNoteIntent : NoteIntent
}