package org.tigeryan.notty.data.database.notes.tuples

import androidx.room.ColumnInfo

data class NoteInsertTuple(
    val title: String,
    val text: String,
    @ColumnInfo(name = "modified_at") val modifiedAt: Long,
    @ColumnInfo(name = "created_at") val createdAt: Long,
)