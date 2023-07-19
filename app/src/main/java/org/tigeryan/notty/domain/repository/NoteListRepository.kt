package org.tigeryan.notty.domain.repository

import kotlinx.coroutines.flow.Flow
import org.tigeryan.notty.domain.model.Note

interface NoteListRepository {

    fun getAllNotes(): Flow<List<Note>>

    fun getNotesByTitle(title: String): Flow<List<Note>>

    suspend fun addNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNode(note: Note)
}