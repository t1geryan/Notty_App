package org.tigeryan.notty.data.database.notes.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes"
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(defaultValue = "0") val id: Long,
    @Embedded val noteData: NoteDataEntity,
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP") val createdAt: Long,
    @ColumnInfo(name = "modified_at", defaultValue = "CURRENT_TIMESTAMP") val modifiedAt: Long
)

data class NoteDataEntity(
    val title: String,
    val text: String,
)