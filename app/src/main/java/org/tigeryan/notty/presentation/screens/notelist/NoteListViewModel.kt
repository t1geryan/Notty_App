package org.tigeryan.notty.presentation.screens.notelist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.tigeryan.mvi.IntentReceiver
import org.tigeryan.notty.domain.repository.NoteListRepository
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteListRepository: NoteListRepository,
) : ViewModel(), IntentReceiver<NoteListIntent> {

    private val _state = MutableStateFlow(NoteListState.loading())
    val state get() = _state.asStateFlow()

    override fun receive(intent: NoteListIntent) {
        when (intent) {
            NoteListIntent.GetAllNotesIntent -> TODO()
            is NoteListIntent.DeleteNoteIntent -> TODO()
            is NoteListIntent.GetNotesByTitle -> TODO()
        }
    }
}