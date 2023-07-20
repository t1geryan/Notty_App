package org.tigeryan.notty.data.mappers.notes

import org.tigeryan.notty.data.database.notes.entities.NoteEntity
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.utils.BidirectionalMapper
import javax.inject.Inject

/**
 * Interface for mapping [Note] <-> [NoteEntity]
 */
interface NoteDomainEntityMapper : BidirectionalMapper<Note, NoteEntity>

class NoteDomainEntityMapperImpl @Inject constructor() : NoteDomainEntityMapper {

    /**
     * Maps [Note] to [NoteEntity]
     */
    override fun map(valueToMap: Note) = with(valueToMap) {
        NoteEntity(
            id = id,
            text = text,
            title = title,
        )
    }

    /**
     * Maps [NoteEntity] to [Note]
     */
    override fun reverseMap(valueToMap: NoteEntity) = with(valueToMap) {
        Note(
            id = id,
            text = text,
            title = title,
        )
    }
}