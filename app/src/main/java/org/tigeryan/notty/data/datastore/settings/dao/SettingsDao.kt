package org.tigeryan.notty.data.datastore.settings.dao

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.tigeryan.notty.data.datastore.dataStore
import org.tigeryan.notty.data.datastore.getValue
import org.tigeryan.notty.data.datastore.setValue
import org.tigeryan.notty.data.datastore.settings.entities.AppThemeEntity
import org.tigeryan.notty.data.datastore.settings.entities.SortingStrategyEntity
import javax.inject.Inject

interface SettingsDao {

    fun getAppTheme(): Flow<AppThemeEntity>

    suspend fun setAppTheme(value: AppThemeEntity)

    fun getSortingStrategy(): Flow<SortingStrategyEntity>

    suspend fun setSortingStrategy(value: SortingStrategyEntity)

    fun getIsDescendingSorting(): Flow<Boolean>

    suspend fun setIsDescendingSorting(value: Boolean)
}

class SettingsDaoImpl @Inject constructor(
    @ApplicationContext context: Context,
) : SettingsDao {

    private val dataStore = context.dataStore

    private val appThemeEntities = AppThemeEntity.values()
    private val sortingStrategyEntities = SortingStrategyEntity.values()

    override fun getAppTheme(): Flow<AppThemeEntity> = dataStore.getValue(
        APP_THEME_PREF_KEY, AppThemeEntity.SYSTEM.ordinal
    ).map {
        appThemeEntities[it]
    }

    override suspend fun setAppTheme(value: AppThemeEntity) =
        dataStore.setValue(APP_THEME_PREF_KEY, value.ordinal)

    override fun getSortingStrategy(): Flow<SortingStrategyEntity> = dataStore.getValue(
        SORTING_STRATEGY_PREF_KEY, SortingStrategyEntity.BY_TITLE.ordinal
    ).map {
        sortingStrategyEntities[it]
    }

    override suspend fun setSortingStrategy(value: SortingStrategyEntity) =
        dataStore.setValue(SORTING_STRATEGY_PREF_KEY, value.ordinal)

    override fun getIsDescendingSorting(): Flow<Boolean> = dataStore.getValue(
        DESCENDING_SORTING_PREF_KEY, false
    )

    override suspend fun setIsDescendingSorting(value: Boolean) =
        dataStore.setValue(DESCENDING_SORTING_PREF_KEY, value)

    companion object {
        private val APP_THEME_PREF_KEY = intPreferencesKey("APP_THEME_PREF_KEY")
        private val SORTING_STRATEGY_PREF_KEY = intPreferencesKey("SORTING_STRATEGY_PREF_KEY")
        private val DESCENDING_SORTING_PREF_KEY = booleanPreferencesKey("DESC_SORT_PREF_KEY")
    }
}