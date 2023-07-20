package org.tigeryan.notty.presentation.navigation

import androidx.navigation.NavController

internal fun NavController.navigateFromNoteListToSettings() {
    navigate(
        route = Route.SETTINGS.route,
    ) {
        popUpTo(Route.NOTE_LIST.route)
        launchSingleTop = true
    }
}

internal fun NavController.navigateFromNoteListToNote(noteId: Long) {
    navigate(
        route = "${Route.NOTE.route}/$noteId",
    ) {
        popUpTo(Route.NOTE_LIST.route)
        launchSingleTop = true
    }
}

internal fun NavController.navigateFromNoteListToSearchScreen() {
    navigate(
        route = Route.SEARCH.route,
    ) {
        popUpTo(Route.NOTE_LIST.route)
        launchSingleTop = true
    }
}