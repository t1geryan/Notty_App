package org.tigeryan.notty.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.tigeryan.notty.data.database.notes.dao.NotesDao
import org.tigeryan.notty.data.mappers.note.NoteDomainEntityMapper
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.domain.repository.NoteListRepository
import javax.inject.Inject

class NoteListRepositoryImpl @Inject constructor(
    private val notesDao: NotesDao,
    private val notesMapper: NoteDomainEntityMapper,
) : NoteListRepository {

    override fun getAllNotes(): Flow<List<Note>> =
        notesDao.getAllNotes().map { list ->
            list.map { entity ->
                notesMapper.reverseMap(entity)
            }
        }

    override fun getNotesByInput(input: String): Flow<List<Note>> =
        notesDao.getNotesByKeyword(input).map { list ->
            list.map { entity ->
                notesMapper.reverseMap(entity)
            }
        }

    override fun getNoteById(id: Long): Flow<Note> =
        notesDao.getNoteById(id).map { list ->
            notesMapper.reverseMap(
                list.firstOrNull()
                    ?: throw IllegalStateException("There is no note with this id in database")
            )
        }

    override suspend fun addNote(note: Note) {
        upsertNoteToDb(note)
    }

    override suspend fun deleteNote(note: Note) {
        notesDao.deleteNoteById(note.id)
    }

    override suspend fun updateNode(note: Note) {
        upsertNoteToDb(note)
    }

    private suspend fun upsertNoteToDb(note: Note) {
        notesDao.upsertNote(
            notesMapper.map(note)
        )
    }
}