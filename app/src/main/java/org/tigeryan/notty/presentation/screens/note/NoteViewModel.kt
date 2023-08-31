package org.tigeryan.notty.presentation.screens.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.tigeryan.mvi.IntentReceiver
import org.tigeryan.notty.domain.model.NoteData
import org.tigeryan.notty.domain.usecase.DeleteNoteUseCase
import org.tigeryan.notty.domain.usecase.GetNoteByIdUseCase
import org.tigeryan.notty.domain.usecase.SaveNoteUseCase
import org.tigeryan.notty.utils.extensions.tryEmitFlow

class NoteViewModel @AssistedInject constructor(
    @Assisted private var noteId: Long?,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
) : ViewModel(), IntentReceiver<NoteIntent> {

    private val _state = MutableStateFlow(NoteState())
    val state get() = _state.asStateFlow()

    private val _events = Channel<Event>(Channel.UNLIMITED, BufferOverflow.DROP_OLDEST)
    val events = _events.receiveAsFlow()

    init {
        noteId?.let { id ->
            viewModelScope.launch {
                tryEmitFlow(viewModelScope) {
                    val note = getNoteByIdUseCase(id).first()
                    _state.apply {
                        value = NoteState(
                            title = note.noteData.title,
                            text = note.noteData.text,
                        )
                    }
                }
            }
        }
    }

    override fun receive(intent: NoteIntent) {
        when (intent) {
            is NoteIntent.UpdateTextIntent -> updateText(intent.text)
            is NoteIntent.UpdateTitleIntent -> updateTitle(intent.title)
            is NoteIntent.DeleteNoteIntent -> deleteNote()
        }
    }

    private fun updateTitle(newTitle: String) {
        _state.apply {
            value = value.copy(title = newTitle)
        }
        saveNote()
    }

    private fun updateText(newText: String) {
        _state.apply {
            value = value.copy(text = newText)
        }
        saveNote()
    }

    private fun saveNote() {
        viewModelScope.launch {
            val (title, text) = state.value
            noteId = saveNoteUseCase(
                noteData = NoteData(title, text),
                id = noteId
            )
        }
    }

    private fun deleteNote() {
        noteId?.let { id ->
            viewModelScope.launch {
                _events.send(
                    Event.ShowDeletionConfirmation(
                        onConfirm = {
                            viewModelScope.launch {
                                deleteNoteUseCase(id)
                                _events.send(Event.NavigateUp)
                            }
                        }
                    )
                )
            }
        }
    }

    sealed interface Event {
        object NavigateUp : Event

        data class ShowDeletionConfirmation(
            val onConfirm: () -> Unit
        ) : Event
    }

    @AssistedFactory
    interface Factory {
        fun create(locationId: Long?): NoteViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            noteId: Long?,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(noteId) as T
            }
        }
    }
}