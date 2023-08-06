package org.tigeryan.notty.presentation.screens.search

import org.tigeryan.mvi.Intent

sealed interface SearchIntent : Intent {

    data class GetNotesByInput(val input: String) : SearchIntent

    data class UpdateInput(val input: String) : SearchIntent
}