package org.tigeryan.notty.presentation.screens.settings

import org.tigeryan.mvi.Intent
import org.tigeryan.notty.domain.model.AppTheme
import org.tigeryan.notty.domain.model.NoteSortingStrategy

sealed interface SettingsIntent : Intent {

    data class UpdateAppTheme(val appTheme: AppTheme) : SettingsIntent

    data class UpdateSortingStrategy(val sortingStrategy: NoteSortingStrategy) : SettingsIntent
}