package org.tigeryan.notty.data.database.notes.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.tigeryan.notty.data.database.notes.entities.NoteEntity
import org.tigeryan.notty.data.database.notes.tuples.NoteInsertTuple
import org.tigeryan.notty.data.database.notes.tuples.NoteUpdateTuple

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Long): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE INSTR(title, :input) > 0 OR INSTR(text, :input) > 0")
    fun getNotesByKeyword(input: String): Flow<List<NoteEntity>>

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNoteById(id: Long)

    @Insert(
        onConflict = OnConflictStrategy.REPLACE,
        entity = NoteEntity::class,
    )
    suspend fun insertNote(noteInsertTuple: NoteInsertTuple): Long

    @Update(
        onConflict = OnConflictStrategy.REPLACE,
        entity = NoteEntity::class,
    )
    suspend fun updateNote(noteUpdateTuple: NoteUpdateTuple)
}