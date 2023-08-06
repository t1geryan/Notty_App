package org.tigeryan.notty.data.datastore.settings.dao

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.tigeryan.notty.data.datastore.dataStore
import org.tigeryan.notty.data.datastore.getValue
import org.tigeryan.notty.data.datastore.setValue
import org.tigeryan.notty.data.datastore.settings.entities.AppThemeEntity
import javax.inject.Inject

interface SettingsDao {

    fun getAppTheme(): Flow<AppThemeEntity>

    suspend fun setAppTheme(value: AppThemeEntity)
}

class SettingsDaoImpl @Inject constructor(
    @ApplicationContext context: Context,
) : SettingsDao {

    private val dataStore = context.dataStore

    private val appThemeEntities = AppThemeEntity.values()

    override fun getAppTheme(): Flow<AppThemeEntity> = dataStore.getValue(
        APP_THEME_PREF_KEY, AppThemeEntity.SYSTEM.ordinal
    ).map {
        appThemeEntities[it]
    }

    override suspend fun setAppTheme(value: AppThemeEntity) =
        dataStore.setValue(APP_THEME_PREF_KEY, value.ordinal)

    companion object {
        private val APP_THEME_PREF_KEY = intPreferencesKey("APP_THEME_PREF_KEY")
    }
}