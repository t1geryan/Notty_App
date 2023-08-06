package org.tigeryan.notty.di.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.tigeryan.notty.data.repository.NoteListRepositoryImpl
import org.tigeryan.notty.data.repository.SettingsRepositoryImpl
import org.tigeryan.notty.domain.repository.NoteListRepository
import org.tigeryan.notty.domain.repository.SettingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    @Singleton
    abstract fun bindNoteListRepository(
        noteListRepositoryImpl: NoteListRepositoryImpl
    ): NoteListRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository
}