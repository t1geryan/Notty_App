package org.tigeryan.notty.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.domain.model.NoteFilter
import org.tigeryan.notty.domain.model.filter
import org.tigeryan.notty.domain.repository.NoteListRepository
import org.tigeryan.notty.domain.repository.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNotesUseCase @Inject constructor(
    private val notesRepository: NoteListRepository,
    private val settingsRepository: SettingsRepository,
) {

    operator fun invoke(
        filter: NoteFilter? = null,
    ): Flow<List<Note>> = combine(
        notesRepository.getAllNotes(),
        settingsRepository.getSortingStrategy(),
    ) { notes, noteSortingStrategy ->
        val result = filter?.let {
            notes.filter(filter)
        } ?: notes
        result.sortedWith(noteSortingStrategy.toNoteComparator())
    }
}