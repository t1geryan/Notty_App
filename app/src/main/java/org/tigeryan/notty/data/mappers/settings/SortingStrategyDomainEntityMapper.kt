package org.tigeryan.notty.data.mappers.settings

import org.tigeryan.notty.data.datastore.settings.entities.SortingStrategyEntity
import org.tigeryan.notty.domain.model.NoteSortingStrategy
import org.tigeryan.notty.utils.BidirectionalMapper
import javax.inject.Inject

/**
 * Interface for mapping [NoteSortingStrategy] <-> [SortingStrategyEntity]
 */
interface SortingStrategyDomainEntityMapper :
    BidirectionalMapper<NoteSortingStrategy, SortingStrategyEntity>

class SortingStrategyDomainEntityMapperImpl @Inject constructor() :
    SortingStrategyDomainEntityMapper {

    /**
     * Maps [NoteSortingStrategy] to [SortingStrategyEntity]
     */
    override fun reverseMap(valueToMap: SortingStrategyEntity): NoteSortingStrategy =
        when (valueToMap) {
            SortingStrategyEntity.BY_TITLE -> NoteSortingStrategy.BY_TITLE
            SortingStrategyEntity.BY_CREATION_TIME -> NoteSortingStrategy.BY_CREATION_TIME
            SortingStrategyEntity.BY_MODIFICATION_TIME -> NoteSortingStrategy.BY_MODIFICATION_TIME
        }

    /**
     * Maps [SortingStrategyEntity] to [NoteSortingStrategy]
     */
    override fun map(valueToMap: NoteSortingStrategy): SortingStrategyEntity =
        when (valueToMap) {
            NoteSortingStrategy.BY_TITLE -> SortingStrategyEntity.BY_TITLE
            NoteSortingStrategy.BY_CREATION_TIME -> SortingStrategyEntity.BY_CREATION_TIME
            NoteSortingStrategy.BY_MODIFICATION_TIME -> SortingStrategyEntity.BY_MODIFICATION_TIME
        }
}