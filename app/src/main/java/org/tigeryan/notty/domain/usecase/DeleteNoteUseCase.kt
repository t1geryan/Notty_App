package org.tigeryan.notty.domain.usecase

import org.tigeryan.notty.domain.repository.NoteListRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteNoteUseCase @Inject constructor(
    private val notesRepository: NoteListRepository
) {

    suspend operator fun invoke(id: Long) {
        notesRepository.deleteNoteById(id)
    }
}