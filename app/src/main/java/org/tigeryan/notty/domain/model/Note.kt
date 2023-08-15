package org.tigeryan.notty.domain.model

data class Note(
    val id: Long,
    val noteData: NoteData,
    val noteTime: NoteTime,
)

/**
 * Note-specific data
 * @param title notes title
 * @param text  notes text
 */
data class NoteData(
    val title: String,
    val text: String,
)

/**
 * Note-specific timestamps
 * @param createdAt creation utc time in millis
 * @param modifiedAt last modification utc time in millis
 */
data class NoteTime(
    val createdAt: Long,
    val modifiedAt: Long,
)
