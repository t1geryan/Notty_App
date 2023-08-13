package org.tigeryan.notty.data.mappers.note

import org.tigeryan.notty.data.database.notes.entities.NoteDataEntity
import org.tigeryan.notty.domain.model.NoteData
import org.tigeryan.notty.utils.BidirectionalMapper
import javax.inject.Inject

/**
 * Interface for mapping [NoteData] <-> [NoteDataEntity]
 */
interface NoteDataDomainEntityMapper : BidirectionalMapper<NoteData, NoteDataEntity>

class NoteDataDomainEntityMapperImpl @Inject constructor() : NoteDataDomainEntityMapper {

    /**
     * Maps [NoteData] to [NoteDataEntity]
     */
    override fun map(valueToMap: NoteData): NoteDataEntity = with(valueToMap) {
        NoteDataEntity(
            title = title,
            text = text
        )
    }

    /**
     * Maps [NoteDataEntity] to [NoteData]
     */
    override fun reverseMap(valueToMap: NoteDataEntity) = with(valueToMap) {
        NoteData(
            title = title,
            text = text
        )
    }
}