package org.tigeryan.notty.di.presentation.viewmodel

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.tigeryan.notty.presentation.screens.note.NoteViewModel

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {

    fun noteViewModelFactory(): NoteViewModel.Factory
}