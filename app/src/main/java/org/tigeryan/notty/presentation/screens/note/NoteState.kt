package org.tigeryan.notty.presentation.screens.note

import androidx.compose.runtime.Immutable

@Immutable
data class NoteState(
    val title: String = "",
    val text: String = "",
)
