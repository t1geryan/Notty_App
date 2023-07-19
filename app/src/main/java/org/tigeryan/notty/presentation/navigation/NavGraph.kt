package org.tigeryan.notty.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    rootNavController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = rootNavController,
        startDestination = Route.NOTE_LIST.route,
        modifier = modifier
    ) {
        noteListScreen(rootNavController)
        noteScreen(rootNavController)
        settingsScreen(rootNavController)
    }
}