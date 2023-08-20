package org.tigeryan.notty.presentation.screens.notelist

import org.tigeryan.notty.domain.model.Note

data class NoteItem(
    val note: Note,
    val isVisible: Boolean,
)