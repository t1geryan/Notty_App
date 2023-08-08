package org.tigeryan.notty.domain.usecase

import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.domain.model.NoteData
import org.tigeryan.notty.domain.repository.NoteListRepository
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(
    private val notesRepository: NoteListRepository,
) {

    suspend operator fun invoke(noteData: NoteData, isCreatingNewNote: Boolean, id: Long?): Long {
        val (title, text) = noteData
        val isNoteEmpty = title.isBlank() && text.isBlank()
        var newId = 0L
        if (isNoteEmpty) {
            id?.let {
                notesRepository.deleteNoteById(id)
            }
        } else {
            id?.let {
                notesRepository.updateNote(
                    Note(
                        id = it,
                        noteData = noteData
                    )
                )
            } ?: run {
                newId = notesRepository.addNote(noteData)
            }
        }
        return id ?: newId
    }
}