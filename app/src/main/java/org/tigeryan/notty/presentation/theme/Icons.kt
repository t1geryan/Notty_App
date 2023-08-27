package org.tigeryan.notty.presentation.theme

import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons as MaterialIcons

data class Icons(
    val settings: ImageVector = MaterialIcons.Outlined.Settings,
    val search: ImageVector = MaterialIcons.Outlined.Search,
    val add: ImageVector = MaterialIcons.Outlined.Add,
    val arrowBack: ImageVector = MaterialIcons.Outlined.ArrowBack,
    val close: ImageVector = MaterialIcons.Outlined.Close,
    val delete: ImageVector = MaterialIcons.Outlined.Delete,
    val expandMore: ImageVector = MaterialIcons.Outlined.ExpandMore,
    val expandLess: ImageVector = MaterialIcons.Outlined.ExpandLess,
    val share: ImageVector = MaterialIcons.Outlined.Share,
)

val LocalIcons = staticCompositionLocalOf { Icons() }

val MaterialTheme.icons: Icons
    @Composable
    @ReadOnlyComposable
    get() = LocalIcons.current
