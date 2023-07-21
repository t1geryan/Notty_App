package org.tigeryan.notty.presentation.navigation

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dagger.hilt.android.EntryPointAccessors
import org.tigeryan.notty.di.presentation.viewmodel.ViewModelFactoryProvider
import org.tigeryan.notty.presentation.screens.note.NoteScreen
import org.tigeryan.notty.presentation.screens.note.NoteViewModel
import org.tigeryan.notty.presentation.screens.notelist.NoteListScreen
import org.tigeryan.notty.presentation.screens.notelist.NoteListViewModel
import org.tigeryan.notty.presentation.screens.search.SearchScreen
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
            onNavigateToEditor = {
                navController.navigateFromNoteListToNote()
            },
            onNavigateToSearch = navController::navigateFromNoteListToSearchScreen,
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
                nullable = true
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val noteIdArgument = backStackEntry.arguments?.getString(NavArg.NOTE_ID_ARG.pathName)
        val factory = EntryPointAccessors.fromActivity(
            LocalContext.current as Activity,
            ViewModelFactoryProvider::class.java
        ).noteViewModelFactory()
        val viewModel: NoteViewModel =
            viewModel(factory = NoteViewModel.provideFactory(factory, noteIdArgument?.toLong()))
        val state by viewModel.state.collectAsStateWithLifecycle()
        NoteScreen(
            state = state,
            onSendIntent = viewModel::receive,
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

internal fun NavGraphBuilder.searchScreen(
    navController: NavController,
) {
    composable(route = Route.SEARCH.route) {
        SearchScreen(
            onNavigateUp = navController::navigateUp,
        )
    }
}