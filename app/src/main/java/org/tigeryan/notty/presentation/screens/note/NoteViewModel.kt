package org.tigeryan.notty.presentation.screens.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.tigeryan.mvi.IntentReceiver
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.domain.model.NoteData
import org.tigeryan.notty.domain.repository.NoteListRepository
import org.tigeryan.notty.utils.extensions.tryEmitFlow
import org.tigeryan.notty.utils.extensions.viewModelScopeIO

class NoteViewModel @AssistedInject constructor(
    @Assisted private var noteId: Long?,
    private val noteListRepository: NoteListRepository,
) : ViewModel(), IntentReceiver<NoteIntent> {

    private val _state = MutableStateFlow(NoteState())
    val state get() = _state.asStateFlow()

    // is the user creates a new note (otherwise edits the old one)
    private val isCreatingNewNote = noteId == null

    init {
        noteId?.let {
            viewModelScopeIO.launch {
                tryEmitFlow(viewModelScopeIO) {
                    val note = noteListRepository.getNoteById(it).first()
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
        val (title, text) = state.value
        val isNoteEmpty = title.isBlank() && text.isBlank()
        viewModelScopeIO.launch {
            /* TODO: Extract to use case */
            if (isCreatingNewNote && isNoteEmpty) {
                noteId?.let { id ->
                    noteListRepository.deleteNoteById(id)
                }
            } else if (!isNoteEmpty) {
                val noteData = NoteData(
                    title = title,
                    text = text,
                )
                noteId?.let {
                    noteListRepository.updateNote(
                        Note(
                            id = it,
                            noteData = noteData
                        )
                    )
                } ?: run {
                    noteId = noteListRepository.addNote(noteData)
                }
            }
        }
    }

    private fun deleteNote() {
        noteId?.let {
            viewModelScopeIO.launch {
                noteListRepository.deleteNoteById(it)
            }
        }
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