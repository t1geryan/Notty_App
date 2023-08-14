package org.tigeryan.notty.domain.usecase

import org.tigeryan.notty.domain.repository.NoteListRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNoteByIdUseCase @Inject constructor(
    private val notesRepository: NoteListRepository,
) {

    operator fun invoke(id: Long) = notesRepository.getNoteById(id)
}