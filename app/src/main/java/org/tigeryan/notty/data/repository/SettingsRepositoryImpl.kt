package org.tigeryan.notty.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.tigeryan.notty.data.datastore.settings.dao.SettingsDao
import org.tigeryan.notty.data.mappers.settings.AppThemeDomainEntityMapper
import org.tigeryan.notty.domain.model.AppTheme
import org.tigeryan.notty.domain.repository.SettingsRepository
import org.tigeryan.notty.utils.extensions.withIOContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDao: SettingsDao,
    private val appThemeMapper: AppThemeDomainEntityMapper,
) : SettingsRepository {

    override fun getCurrentAppTheme(): Flow<AppTheme> = settingsDao.getAppTheme().map { entity ->
        appThemeMapper.reverseMap(entity)
    }.flowOn(Dispatchers.IO)

    override suspend fun setAppTheme(value: AppTheme) = withIOContext {
        settingsDao.setAppTheme(
            appThemeMapper.map(value)
        )
    }
}