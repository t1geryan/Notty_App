package org.tigeryan.notty.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.tigeryan.notty.data.mappers.note.NoteDomainEntityMapper
import org.tigeryan.notty.data.mappers.note.NoteDomainEntityMapperImpl
import org.tigeryan.notty.data.mappers.settings.AppThemeDomainEntityMapper
import org.tigeryan.notty.data.mappers.settings.AppThemeDomainEntityMapperImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MappersModule {

    @Binds
    @Singleton
    abstract fun bindsNoteMapper(
        noteDomainEntityMapperImpl: NoteDomainEntityMapperImpl
    ): NoteDomainEntityMapper

    @Binds
    @Singleton
    abstract fun bindsAppThemeMapper(
        appThemeDomainEntityMapperImpl: AppThemeDomainEntityMapperImpl
    ): AppThemeDomainEntityMapper
}