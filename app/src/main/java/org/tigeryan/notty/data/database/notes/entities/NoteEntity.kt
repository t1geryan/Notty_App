package org.tigeryan.notty.data.database.notes.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes"
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val text: String,
)
