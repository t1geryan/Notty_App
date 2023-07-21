package org.tigeryan.notty.presentation.screens.notelist

import org.tigeryan.mvi.Intent
import org.tigeryan.notty.domain.model.Note

sealed interface NoteListIntent : Intent {

    object GetAllNotesIntent : NoteListIntent

    data class DeleteNoteIntent(val note: Note) : NoteListIntent
}
