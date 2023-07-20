package org.tigeryan.notty.presentation.screens.notelist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.tigeryan.mvi.IntentReceiver
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.domain.repository.NoteListRepository
import org.tigeryan.notty.utils.extensions.viewModelScopeIO
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteListRepository: NoteListRepository,
) : ViewModel(), IntentReceiver<NoteListIntent> {

    private val _state = MutableStateFlow(NoteListState.loading())
    val state get() = _state.asStateFlow()

    init {
        fetchNotes()
    }

    override fun receive(intent: NoteListIntent) {
        when (intent) {
            is NoteListIntent.GetAllNotesIntent -> fetchNotes()
            is NoteListIntent.DeleteNoteIntent -> deleteNote(intent.note)
        }
    }

    private fun fetchNotes() {
        viewModelScopeIO.launch {
            _state.apply {
                value = NoteListState.loading()
                try {
                    noteListRepository.getAllNotes().collect { notes ->
                        value = NoteListState.success(notes)
                    }
                } catch (e: Exception) {
                    value = NoteListState.failure(e)
                }
            }
        }
    }


    private fun deleteNote(note: Note) {
        viewModelScopeIO.launch {
            noteListRepository.deleteNote(note)
        }
    }
}