package org.tigeryan.notty.presentation.screens.notelist

import androidx.compose.runtime.Immutable
import org.tigeryan.mvi.StateCreator
import org.tigeryan.notty.domain.model.Note

@Immutable
data class NoteListState(
    val notes: List<NoteItem>,
    val selectedNote: Note? = null,
    val isLoading: Boolean,
    val isFailed: Boolean,
    val exception: Exception?,
) {

    companion object : StateCreator<NoteListState, List<NoteItem>> {

        override fun success(data: List<NoteItem>): NoteListState = NoteListState(
            notes = data,
            selectedNote = null,
            isLoading = false,
            isFailed = false,
            exception = null,
        )

        override fun failure(exception: Exception?): NoteListState = NoteListState(
            notes = emptyList(),
            selectedNote = null,
            isLoading = false,
            isFailed = true,
            exception = exception,
        )

        override fun loading(): NoteListState = NoteListState(
            notes = emptyList(),
            selectedNote = null,
            isLoading = true,
            isFailed = false,
            exception = null,
        )
    }
}