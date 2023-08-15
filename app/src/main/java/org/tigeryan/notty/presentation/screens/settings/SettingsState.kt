package org.tigeryan.notty.presentation.screens.settings

import androidx.compose.runtime.Immutable
import org.tigeryan.notty.domain.model.AppTheme
import org.tigeryan.notty.domain.model.NoteSortingStrategy


@Immutable
data class Settings(
    val appTheme: AppTheme = AppTheme.UNDEFINED,
    val sortingStrategy: NoteSortingStrategy = NoteSortingStrategy.BY_TITLE,
    val isDescendingSort: Boolean = false,
)

@Immutable
data class SettingsState(
    val settings: Settings = Settings(),
    val isLoading: Boolean = false,
)