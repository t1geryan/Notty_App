package org.tigeryan.notty.di.data.local

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.tigeryan.notty.data.datastore.settings.dao.SettingsDao
import org.tigeryan.notty.data.datastore.settings.dao.SettingsDaoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsDatastoreModule {

    @Binds
    @Singleton
    abstract fun bindSettingsDao(
        settingsDaoImpl: SettingsDaoImpl
    ): SettingsDao
}