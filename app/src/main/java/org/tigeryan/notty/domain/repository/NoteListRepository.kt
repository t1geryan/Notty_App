package org.tigeryan.notty.domain.repository

import kotlinx.coroutines.flow.Flow
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.domain.model.NoteData

interface NoteListRepository {

    fun getAllNotes(): Flow<List<Note>>

    fun getNotesByInput(input: String): Flow<List<Note>>

    fun getNoteById(id: Long): Flow<Note>

    suspend fun addNote(noteData: NoteData): Long

    suspend fun deleteNoteById(id: Long)

    suspend fun updateNote(note: Note)
}