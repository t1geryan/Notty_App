package org.tigeryan.notty.presentation.screens.search

import androidx.compose.runtime.Immutable
import org.tigeryan.mvi.StateCreator
import org.tigeryan.notty.domain.model.Note

data class SearchResult(
    val notes: List<Note>,
    val input: String,
)

@Immutable
data class SearchState(
    val notes: List<Note>,
    val isLoading: Boolean,
    val isFailed: Boolean,
    val exception: Exception?,
    val input: String,
) {

    companion object : StateCreator<SearchState, SearchResult> {

        fun empty() = SearchState(
            notes = emptyList(),
            isLoading = false,
            isFailed = false,
            exception = null,
            input = "",
        )

        override fun success(data: SearchResult) = SearchState(
            notes = data.notes,
            isLoading = false,
            isFailed = false,
            exception = null,
            input = data.input,
        )

        override fun failure(exception: Exception?) = SearchState(
            notes = emptyList(),
            isLoading = false,
            isFailed = true,
            exception = exception,
            input = "",
        )

        override fun loading() = SearchState(
            notes = emptyList(),
            input = "",
            isLoading = true,
            isFailed = false,
            exception = null,
        )
    }
}