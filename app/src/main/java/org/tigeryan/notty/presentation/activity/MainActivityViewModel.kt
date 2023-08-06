package org.tigeryan.notty.presentation.activity

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.tigeryan.notty.domain.model.AppTheme
import org.tigeryan.notty.domain.repository.SettingsRepository
import org.tigeryan.notty.utils.extensions.viewModelScopeIO
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _appTheme = MutableStateFlow(AppTheme.UNDEFINED)
    val appTheme get() = _appTheme.asStateFlow()

    init {
        viewModelScopeIO.launch {
            settingsRepository.getCurrentAppTheme().collect { appTheme ->
                _appTheme.value = appTheme
            }
        }
    }
}