package org.tigeryan.notty.di.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.tigeryan.notty.data.database.notes.db.NotesDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotesDatabaseModule {

    private const val databaseName = "notes.db"

    @Provides
    @Singleton
    fun provideNotesDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, NotesDatabase::class.java, databaseName)
            .build()

    @Provides
    @Singleton
    fun provideNotesDao(notesDatabase: NotesDatabase) = notesDatabase.getNotesDao()
}