package org.tigeryan.notty.data.mappers.settings

import org.tigeryan.notty.data.datastore.settings.entities.AppThemeEntity
import org.tigeryan.notty.domain.model.AppTheme
import org.tigeryan.notty.utils.BidirectionalMapper
import javax.inject.Inject

/**
 * Interface for mapping [AppTheme] <-> [AppThemeEntity]
 */
interface AppThemeDomainEntityMapper : BidirectionalMapper<AppTheme, AppThemeEntity>

class AppThemeDomainEntityMapperImpl @Inject constructor() : AppThemeDomainEntityMapper {

    /**
     * Maps [AppTheme] to [AppThemeEntity]
     */
    override fun reverseMap(valueToMap: AppThemeEntity): AppTheme = when (valueToMap) {
        AppThemeEntity.DAY -> AppTheme.DAY
        AppThemeEntity.NIGHT -> AppTheme.NIGHT
        AppThemeEntity.SYSTEM -> AppTheme.SYSTEM
    }

    /**
     * Maps [AppThemeEntity] to [AppTheme]
     */
    override fun map(valueToMap: AppTheme): AppThemeEntity = when (valueToMap) {
        AppTheme.DAY -> AppThemeEntity.DAY
        AppTheme.NIGHT -> AppThemeEntity.NIGHT
        AppTheme.SYSTEM -> AppThemeEntity.SYSTEM
    }
}