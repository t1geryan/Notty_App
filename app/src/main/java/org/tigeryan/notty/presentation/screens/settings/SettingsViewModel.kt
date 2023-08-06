package org.tigeryan.notty.presentation.screens.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.tigeryan.mvi.IntentReceiver
import org.tigeryan.notty.domain.model.AppTheme
import org.tigeryan.notty.domain.repository.SettingsRepository
import org.tigeryan.notty.utils.extensions.viewModelScopeIO
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel(), IntentReceiver<SettingsIntent> {

    private val _state = MutableStateFlow<SettingsState>(SettingsState.Loading)
    val state get() = _state.asStateFlow()

    init {
        viewModelScopeIO.launch {
            settingsRepository.getCurrentAppTheme().collect { appTheme ->
                _state.value = SettingsState.Success(appTheme)
            }
        }
    }

    override fun receive(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.UpdateAppTheme -> setAppTheme(intent.appTheme)
        }
    }

    private fun setAppTheme(appTheme: AppTheme) = viewModelScopeIO.launch {
        settingsRepository.setAppTheme(appTheme)
    }
}