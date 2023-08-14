package org.tigeryan.notty.domain.repository

import kotlinx.coroutines.flow.Flow
import org.tigeryan.notty.domain.model.AppTheme
import org.tigeryan.notty.domain.model.NoteSortingStrategy

interface SettingsRepository {

    fun getCurrentAppTheme(): Flow<AppTheme>

    suspend fun setAppTheme(value: AppTheme)

    fun getSortingStrategy(): Flow<NoteSortingStrategy>

    suspend fun setSortingStrategy(value: NoteSortingStrategy)
}