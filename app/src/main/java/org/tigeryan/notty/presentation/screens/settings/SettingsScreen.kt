package org.tigeryan.notty.presentation.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.tigeryan.notty.R
import org.tigeryan.notty.presentation.screens.settings.views.SettingDropdownMenu
import org.tigeryan.notty.presentation.screens.settings.views.SettingDropdownMenuModel
import org.tigeryan.notty.presentation.views.Action
import org.tigeryan.notty.presentation.views.AppActionBar
import org.tigeryan.notty.utils.extensions.rememberMutableStateOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppActionBar(
                title = stringResource(id = R.string.settings_screen_title),
                navigationAction = Action.navigateUpAction(
                    title = stringResource(id = R.string.navigate_up_action_title),
                    onClick = onNavigateUp,
                )
            )
        },
    ) { paddingValues ->
        // TODO: Replace sample by real settings screen
        var currentAppThemeIndex by rememberMutableStateOf(value = 0)

        Column(
            modifier = Modifier
                .padding(paddingValues),
        ) {
            SettingDropdownMenu(
                model = SettingDropdownMenuModel(
                    title = stringResource(R.string.app_theme_setting_title),
                    currentIndex = currentAppThemeIndex,
                    values = listOf(
                        stringResource(R.string.app_theme_setting_day),
                        stringResource(R.string.app_theme_setting_night),
                        stringResource(R.string.app_theme_setting_system)
                    ),
                ),
                onItemSelected = { index ->
                    currentAppThemeIndex = index
                },
                modifier = Modifier
                    .fillMaxWidth(),
            )
            SettingDropdownMenu(
                model = SettingDropdownMenuModel(
                    title = stringResource(R.string.app_theme_setting_title),
                    currentIndex = currentAppThemeIndex,
                    values = listOf(
                        stringResource(R.string.app_theme_setting_day),
                        stringResource(R.string.app_theme_setting_night),
                        stringResource(R.string.app_theme_setting_system)
                    ),
                ),
                onItemSelected = { index ->
                    currentAppThemeIndex = index
                },
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}