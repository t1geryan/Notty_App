package org.tigeryan.notty.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dimen(
    val mainTextSize: TextUnit = 18.sp,
    val titleTextSize: TextUnit = 22.sp,

    val baseIconSize: Dp = 36.dp,
    val minIconBoxSize: Dp = 48.dp,
    val avatarSize: Dp = 48.dp,

    val refreshIndicatorThreshold: Dp = 68.dp,

    val wizardButtonFractionalWidth: Float = 0.7f
)

val LocalDimen = staticCompositionLocalOf { Dimen() }

val MaterialTheme.dimens: Dimen
    @Composable
    @ReadOnlyComposable
    get() = LocalDimen.current