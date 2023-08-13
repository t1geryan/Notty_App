package org.tigeryan.notty.domain.model

data class NoteData(
    val title: String,
    val text: String,
)

data class NoteTime(
    val createdAt: Long,
    val modifiedAt: Long,
)

data class Note(
    val id: Long,
    val noteData: NoteData,
    val noteTime: NoteTime,
)