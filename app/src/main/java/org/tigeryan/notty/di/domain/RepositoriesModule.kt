package org.tigeryan.notty.di.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.tigeryan.notty.data.repository.NoteListRepositoryImpl
import org.tigeryan.notty.domain.repository.NoteListRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    @Singleton
    abstract fun bindsNoteListRepository(
        noteListRepositoryImpl: NoteListRepositoryImpl
    ): NoteListRepository
}