package org.tigeryan.notty.domain.repository

import kotlinx.coroutines.flow.Flow
import org.tigeryan.notty.domain.model.AppTheme

interface SettingsRepository {

    fun getCurrentAppTheme(): Flow<AppTheme>

    suspend fun setAppTheme(value: AppTheme)
}