package org.tigeryan.notty.domain.model

data class NoteData(
    val title: String,
    val text: String
)

data class Note(
    val id: Long,
    val noteData: NoteData,
)
