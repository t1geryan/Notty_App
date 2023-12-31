package org.tigeryan.notty.presentation.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.tigeryan.notty.R
import org.tigeryan.notty.domain.model.AppTheme
import org.tigeryan.notty.domain.model.NoteSortingStrategy
import org.tigeryan.notty.presentation.screens.settings.views.DropdownMenuSetting
import org.tigeryan.notty.presentation.screens.settings.views.DropdownMenuSettingModel
import org.tigeryan.notty.presentation.screens.settings.views.ToggleSetting
import org.tigeryan.notty.presentation.screens.settings.views.ToggleSettingModel
import org.tigeryan.notty.presentation.views.Action
import org.tigeryan.notty.presentation.views.AppActionBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsState,
    onSendIntent: (SettingsIntent) -> Unit,
    onNavigateUp: () -> Unit,
) {
    val appThemes = remember {
        AppTheme.values()
    }
    val sortingStrategies = remember {
        NoteSortingStrategy.values()
    }

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            with(state) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center),
                    )
                } else {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        DropdownMenuSetting(
                            model = DropdownMenuSettingModel(
                                title = stringResource(R.string.app_theme_setting_title),
                                currentIndex = settings.appTheme.ordinal,
                                values = listOf(
                                    stringResource(R.string.app_theme_setting_day),
                                    stringResource(R.string.app_theme_setting_night),
                                    stringResource(R.string.app_theme_setting_system)
                                ),
                            ),
                            onItemSelected = { index ->
                                onSendIntent(SettingsIntent.UpdateAppTheme(appThemes[index]))
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                        )

                        DropdownMenuSetting(
                            model = DropdownMenuSettingModel(
                                title = stringResource(R.string.sorting_strategy_setting_title),
                                currentIndex = settings.sortingStrategy.ordinal,
                                values = listOf(
                                    stringResource(R.string.sorting_strategy_setting_by_title),
                                    stringResource(R.string.sorting_strategy_setting_by_creation),
                                    stringResource(R.string.sorting_strategy_setting_by_modification),
                                ),
                            ),
                            onItemSelected = { index ->
                                onSendIntent(SettingsIntent.UpdateSortingStrategy(sortingStrategies[index]))
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                        )

                        ToggleSetting(
                            model = ToggleSettingModel(
                                title = stringResource(R.string.descending_sort_setting_title),
                                isChecked = state.settings.isDescendingSort,
                            ),
                            onCheckedChange = { isChecked ->
                                onSendIntent(SettingsIntent.UpdateIsDescendingSorting(isChecked))
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}