package org.tigeryan.notty.presentation.screens.settings

import org.tigeryan.mvi.Intent
import org.tigeryan.notty.domain.model.AppTheme

sealed interface SettingsIntent : Intent {

    data class UpdateAppTheme(val appTheme: AppTheme) : SettingsIntent
}