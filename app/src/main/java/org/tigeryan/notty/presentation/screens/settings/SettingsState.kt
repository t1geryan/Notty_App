package org.tigeryan.notty.presentation.screens.settings

import androidx.compose.runtime.Immutable
import org.tigeryan.notty.domain.model.AppTheme


@Immutable
data class Settings(
    val appTheme: AppTheme = AppTheme.UNDEFINED,
)

@Immutable
data class SettingsState(
    val settings: Settings = Settings(),
    val isLoading: Boolean = false,
)