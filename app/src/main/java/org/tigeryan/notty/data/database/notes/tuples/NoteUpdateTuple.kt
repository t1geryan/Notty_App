package org.tigeryan.notty.data.database.notes.tuples

import androidx.room.ColumnInfo

data class NoteUpdateTuple(
    val id: Long,
    val title: String,
    val text: String,
    @ColumnInfo(name = "modified_at") val modifiedAt: Long,
)