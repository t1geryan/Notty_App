package org.tigeryan.notty.di.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.tigeryan.notty.domain.repository.NoteListRepository
import org.tigeryan.notty.domain.usecase.GetNoteByIdUseCase
import org.tigeryan.notty.domain.usecase.GetNotesUseCase
import org.tigeryan.notty.domain.usecase.SaveNoteUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteUseCasesModule {

    @Provides
    @Singleton
    fun provideGetNotesUseCase(
        notesRepository: NoteListRepository
    ) = GetNotesUseCase(notesRepository)

    @Provides
    @Singleton
    fun provideGetNoteUseCase(
        notesRepository: NoteListRepository
    ) = GetNoteByIdUseCase(notesRepository)

    @Provides
    @Singleton
    fun provideSaveNoteUseCase(
        notesRepository: NoteListRepository
    ) = SaveNoteUseCase(notesRepository)
}