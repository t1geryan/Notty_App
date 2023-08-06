package org.tigeryan.notty.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.tigeryan.notty.data.datastore.settings.dao.SettingsDao
import org.tigeryan.notty.data.mappers.settings.AppThemeDomainEntityMapper
import org.tigeryan.notty.domain.model.AppTheme
import org.tigeryan.notty.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDao: SettingsDao,
    private val appThemeMapper: AppThemeDomainEntityMapper,
) : SettingsRepository {

    override fun getCurrentAppTheme(): Flow<AppTheme> = settingsDao.getAppTheme().map { entity ->
        appThemeMapper.reverseMap(entity)
    }

    override suspend fun setAppTheme(value: AppTheme) =
        settingsDao.setAppTheme(
            appThemeMapper.map(value)
        )
}