package org.tigeryan.notty.presentation.screens.notelist

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.tigeryan.mvi.IntentReceiver
import org.tigeryan.notty.R
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.domain.usecase.DeleteNoteUseCase
import org.tigeryan.notty.domain.usecase.GetNotesUseCase
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel(), IntentReceiver<NoteListIntent> {

    private val _state = MutableStateFlow(NoteListState.loading())
    val state get() = _state.asStateFlow()

    private val _events = Channel<Event>(Channel.UNLIMITED, BufferOverflow.DROP_OLDEST)
    val events = _events.receiveAsFlow()

    private val noteBin = mutableListOf<Long>()

    init {
        fetchNotes()
    }

    override fun receive(intent: NoteListIntent) {
        when (intent) {
            is NoteListIntent.GetAllNotesIntent -> fetchNotes()
            is NoteListIntent.DeleteNoteIntent -> deleteNote(intent.note)
            is NoteListIntent.SelectNoteIntent -> selectNote(intent.note)
        }
    }

    private fun fetchNotes() {
        viewModelScope.launch {
            _state.apply {
                value = NoteListState.loading()
                try {
                    getNotesUseCase().collect { notes ->
                        value = NoteListState.success(notes.map {
                            NoteItem(
                                note = it,
                                isVisible = true
                            )
                        })
                    }
                } catch (e: Exception) {
                    value = NoteListState.failure(e)
                }
            }
        }
    }

    private fun selectNote(note: Note) {
        _state.apply {
            value = value.copy(selectedNote = note)
            viewModelScope.launch {
                _events.send(Event.ShowBottomSheetForNote(note))
            }
        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            updateNoteVisibility(note.id, false)
            noteBin.add(note.id)
            _events.send(
                Event.ShowSnackbar(
                    message = R.string.deletion_undo_message,
                    actionTitle = R.string.undo,
                    onAction = {
                        updateNoteVisibility(note.id, true)
                        noteBin.remove(note.id)
                        clearNoteBin()
                    },
                    onDismiss = {
                        clearNoteBin()
                    },
                )
            )
        }
    }

    private fun updateNoteVisibility(id: Long, isVisible: Boolean) {
        _state.value = with(_state.value) {
            copy(
                notes = notes.map {
                    if (it.note.id == id)
                        it.copy(isVisible = isVisible)
                    else it
                }
            )
        }
    }

    private fun clearNoteBin() {
        viewModelScope.launch {
            noteBin.forEach {
                deleteNoteUseCase(it)
            }
            noteBin.clear()
        }
    }

    sealed interface Event {

        data class ShowBottomSheetForNote(
            val note: Note,
        ) : Event

        data class ShowSnackbar(
            @StringRes val message: Int,
            @StringRes val actionTitle: Int,
            val onAction: () -> Unit = {},
            val onDismiss: () -> Unit = {},
        ) : Event
    }
}