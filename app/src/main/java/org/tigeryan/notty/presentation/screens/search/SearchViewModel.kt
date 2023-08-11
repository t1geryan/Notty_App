package org.tigeryan.notty.presentation.screens.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.tigeryan.mvi.IntentReceiver
import org.tigeryan.notty.domain.model.NoteInputFilter
import org.tigeryan.notty.domain.usecase.GetNotesUseCase
import org.tigeryan.notty.utils.extensions.viewModelScopeIO
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
) : ViewModel(), IntentReceiver<SearchIntent> {

    private val _state = MutableStateFlow(SearchState.empty())
    val state get() = _state.asStateFlow()

    override fun receive(intent: SearchIntent) {
        when (intent) {
            is SearchIntent.GetNotesByInput -> fetchNotesByInput(intent.input)
            is SearchIntent.UpdateInput -> updateInput(intent.input)
        }
    }

    private fun fetchNotesByInput(input: String) {
        viewModelScopeIO.launch {
            with(_state) {
                value = SearchState.loading()
                try {
                    getNotesUseCase(filter = NoteInputFilter(input)).collect { notes ->
                        value = SearchState.success(
                            SearchResult(
                                notes = notes,
                                input = input,
                            )
                        )
                    }
                } catch (e: Exception) {
                    value = SearchState.failure(e)
                }
            }
        }
    }

    private fun updateInput(input: String) {
        _state.value = _state.value.copy(input = input)
        fetchNotesByInput(input)
    }
}