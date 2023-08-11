package org.tigeryan.notty.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.tigeryan.mvi.IntentReceiver
import org.tigeryan.notty.domain.model.AppTheme
import org.tigeryan.notty.domain.repository.SettingsRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel(), IntentReceiver<SettingsIntent> {

    private val _state = MutableStateFlow(SettingsState(isLoading = true))
    val state get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.getCurrentAppTheme().collect { appTheme ->
                _state.value = _state.value.copy(settings = Settings(appTheme), isLoading = false)
            }
        }
    }

    override fun receive(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.UpdateAppTheme -> setAppTheme(intent.appTheme)
        }
    }

    private fun setAppTheme(appTheme: AppTheme) {
        viewModelScope.launch {
            settingsRepository.setAppTheme(appTheme)
        }
    }
}