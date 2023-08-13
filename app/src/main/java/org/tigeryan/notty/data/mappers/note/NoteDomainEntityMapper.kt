package org.tigeryan.notty.data.mappers.note

import org.tigeryan.notty.data.database.notes.entities.NoteEntity
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.domain.model.NoteTime
import org.tigeryan.notty.utils.BidirectionalMapper
import javax.inject.Inject

/**
 * Interface for mapping [Note] <-> [NoteEntity]
 */
interface NoteDomainEntityMapper : BidirectionalMapper<Note, NoteEntity>

class NoteDomainEntityMapperImpl @Inject constructor(
    private val noteDataDomainEntityMapper: NoteDataDomainEntityMapper,
) : NoteDomainEntityMapper {

    /**
     * Maps [Note] to [NoteEntity]
     */
    override fun map(valueToMap: Note) = with(valueToMap) {
        NoteEntity(
            id = id,
            noteData = noteDataDomainEntityMapper.map(noteData),
            createdAt = noteTime.createdAt,
            modifiedAt = noteTime.modifiedAt,
        )
    }

    /**
     * Maps [NoteEntity] to [Note]
     */
    override fun reverseMap(valueToMap: NoteEntity) = with(valueToMap) {
        Note(
            id = id,
            noteData = noteDataDomainEntityMapper.reverseMap(noteData),
            noteTime = NoteTime(
                createdAt = createdAt,
                modifiedAt = modifiedAt,
            ),
        )
    }
}