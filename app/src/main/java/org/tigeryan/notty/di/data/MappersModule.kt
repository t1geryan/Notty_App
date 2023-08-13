package org.tigeryan.notty.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.tigeryan.notty.data.mappers.note.NoteDataDomainEntityMapper
import org.tigeryan.notty.data.mappers.note.NoteDataDomainEntityMapperImpl
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
    abstract fun bindNoteMapper(
        noteDomainEntityMapperImpl: NoteDomainEntityMapperImpl
    ): NoteDomainEntityMapper

    @Binds
    @Singleton
    abstract fun bindNoteDataMapper(
        noteDataDomainEntityMapperImpl: NoteDataDomainEntityMapperImpl
    ): NoteDataDomainEntityMapper

    @Binds
    @Singleton
    abstract fun bindAppThemeMapper(
        appThemeDomainEntityMapperImpl: AppThemeDomainEntityMapperImpl
    ): AppThemeDomainEntityMapper
}