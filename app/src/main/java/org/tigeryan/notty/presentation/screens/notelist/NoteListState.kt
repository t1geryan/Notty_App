package org.tigeryan.notty.presentation.screens.notelist

import androidx.compose.runtime.Immutable
import org.tigeryan.mvi.StateCreator
import org.tigeryan.notty.domain.model.Note

@Immutable
data class NoteListState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val isFailed: Boolean = false,
    val exception: Exception? = null,
) {

    companion object : StateCreator<NoteListState, List<Note>> {

        override fun success(data: List<Note>): NoteListState = NoteListState(
            notes = data,
            isLoading = false,
            isFailed = false,
            exception = null,
        )

        override fun failure(exception: Exception?): NoteListState = NoteListState(
            notes = emptyList(),
            isLoading = false,
            isFailed = true,
            exception = exception,
        )

        override fun loading(): NoteListState = NoteListState(
            notes = emptyList(),
            isLoading = true,
            isFailed = false,
            exception = null,
        )
    }
}