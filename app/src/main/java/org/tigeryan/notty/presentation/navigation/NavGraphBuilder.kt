package org.tigeryan.notty.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.tigeryan.notty.presentation.screens.note.NoteScreen
import org.tigeryan.notty.presentation.screens.notelist.NoteListScreen
import org.tigeryan.notty.presentation.screens.notelist.NoteListViewModel
import org.tigeryan.notty.presentation.screens.settings.SettingsScreen

internal fun NavGraphBuilder.noteListScreen(
    navController: NavController,
) {
    composable(route = Route.NOTE_LIST.route) {
        val viewModel: NoteListViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        NoteListScreen(
            state = state,
            onSendIntent = viewModel::receive,
            onNavigateToNote = navController::navigateFromNoteListToNote,
            onNavigateToSettings = navController::navigateFromNoteListToSettings,
        )
    }
}

internal fun NavGraphBuilder.noteScreen(
    navController: NavController,
) {
    composable(
        route = "${Route.NOTE.route}/{${NavArg.NOTE_ID_ARG.pathName}}",
        arguments = listOf(
            navArgument(name = NavArg.NOTE_ID_ARG.pathName) {
                type = NavType.LongType
            }
        )
    ) { backStackEntry ->
        val noteIdArgument = backStackEntry.arguments?.getLong(NavArg.NOTE_ID_ARG.pathName)
            ?: throw IllegalStateException("Argument ${NavArg.NOTE_ID_ARG.pathName} was not passed")
        // TODO: use noteIdArgument
        NoteScreen(
            onNavigateUp = navController::navigateUp,
        )
    }
}

internal fun NavGraphBuilder.settingsScreen(
    navController: NavController,
) {
    composable(route = Route.SETTINGS.route) {
        SettingsScreen(
            onNavigateUp = navController::navigateUp,
        )
    }
}