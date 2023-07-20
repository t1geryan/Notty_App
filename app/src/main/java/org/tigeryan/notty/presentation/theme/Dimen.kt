package org.tigeryan.notty.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

data class Dimen(
    val singleTextLinesCount: Int = 1,
    val smallTextLinesCount: Int = 3,
)

val LocalDimen = staticCompositionLocalOf { Dimen() }

val MaterialTheme.dimens: Dimen
    @Composable
    @ReadOnlyComposable
    get() = LocalDimen.current