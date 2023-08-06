package org.tigeryan.notty.presentation.screens.settings

import androidx.compose.runtime.Immutable
import org.tigeryan.notty.domain.model.AppTheme

@Immutable
sealed interface SettingsState {

    data class Success(
        val appTheme: AppTheme
    ) : SettingsState

    object Loading : SettingsState
}