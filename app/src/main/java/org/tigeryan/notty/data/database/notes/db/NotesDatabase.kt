package org.tigeryan.notty.data.database.notes.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.tigeryan.notty.data.database.notes.dao.NotesDao
import org.tigeryan.notty.data.database.notes.entities.NoteEntity

@Database(
    version = 1,
    entities = [
        NoteEntity::class,
    ]
)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao
}