package org.tigeryan.notty.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.domain.model.NoteFilter
import org.tigeryan.notty.domain.repository.NoteListRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val notesRepository: NoteListRepository,
) {

    operator fun invoke(filter: NoteFilter? = null): Flow<List<Note>> {
        return notesRepository.getAllNotes().map { notes ->
            notes.filter { note ->
                filter?.invoke(note) ?: true
            }
        }
    }
}