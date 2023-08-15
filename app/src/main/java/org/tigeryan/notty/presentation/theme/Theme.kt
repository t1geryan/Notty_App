package org.tigeryan.notty.presentation.theme

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    surface = DarkGrey,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    surface = LightGrey,
)

@Composable
fun NottyTheme(
    dimens: Dimens = MaterialTheme.dimens,
    spacing: Spacing = MaterialTheme.spacing,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    SideEffect {
        changeSystemBarsColor(view, colorScheme.background, darkTheme)
    }

    CompositionLocalProvider(
        LocalDimens provides dimens,
        LocalSpacing provides spacing,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

private fun changeSystemBarsColor(
    view: View,
    backgroundColor: Color,
    darkTheme: Boolean
) {
    val backgroundColorArgb = backgroundColor.toArgb()

    if (!view.isInEditMode && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        // change system bars background color
        val window = (view.context as Activity).window
        window.statusBarColor = backgroundColorArgb
        window.navigationBarColor = backgroundColorArgb

        // change status bar icons color
        window.decorView.windowInsetsController?.let {
            if (darkTheme) {
                // icons color to white
                it.setSystemBarsAppearance(0, APPEARANCE_LIGHT_STATUS_BARS)
            } else {
                // icons color to black
                it.setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS)
            }
        }
    }
}