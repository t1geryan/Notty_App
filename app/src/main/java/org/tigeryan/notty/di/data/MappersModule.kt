package org.tigeryan.notty.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.tigeryan.notty.data.mappers.notes.NoteDomainEntityMapper
import org.tigeryan.notty.data.mappers.notes.NoteDomainEntityMapperImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MappersModule {

    @Binds
    @Singleton
    abstract fun bindsNoteMapper(
        noteDomainEntityMapperImpl: NoteDomainEntityMapperImpl
    ): NoteDomainEntityMapper
}