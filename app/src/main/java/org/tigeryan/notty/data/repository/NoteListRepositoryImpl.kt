package org.tigeryan.notty.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.tigeryan.notty.data.database.notes.dao.NotesDao
import org.tigeryan.notty.data.mappers.note.NoteDataDomainEntityMapper
import org.tigeryan.notty.data.mappers.note.NoteDomainEntityMapper
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.domain.model.NoteData
import org.tigeryan.notty.domain.repository.NoteListRepository
import org.tigeryan.notty.utils.extensions.withIOContext
import javax.inject.Inject

class NoteListRepositoryImpl @Inject constructor(
    private val notesDao: NotesDao,
    private val notesMapper: NoteDomainEntityMapper,
    private val noteDataMapper: NoteDataDomainEntityMapper,
) : NoteListRepository {

    override fun getAllNotes(): Flow<List<Note>> =
        notesDao.getAllNotes().map { list ->
            list.map { entity ->
                notesMapper.reverseMap(entity)
            }
        }.flowOn(Dispatchers.IO)

    override fun getNotesByInput(input: String): Flow<List<Note>> =
        notesDao.getNotesByKeyword(input).map { list ->
            list.map { entity ->
                notesMapper.reverseMap(entity)
            }
        }.flowOn(Dispatchers.IO)

    override fun getNoteById(id: Long): Flow<Note> =
        notesDao.getNoteById(id).map { list ->
            notesMapper.reverseMap(
                list.firstOrNull()
                    ?: throw IllegalStateException("There is no note with this id in database")
            )
        }.flowOn(Dispatchers.IO)

    override suspend fun addNote(noteData: NoteData): Long = withIOContext {
        notesDao.insertNote(noteDataMapper.map(noteData))
    }

    override suspend fun deleteNoteById(id: Long) {
        withIOContext {
            notesDao.deleteNoteById(id)
        }
    }

    override suspend fun updateNote(id: Long, noteData: NoteData) {
        withIOContext {
            notesDao.updateNote(
                id = id,
                title = noteData.title,
                text = noteData.text,
            )
        }
    }
}