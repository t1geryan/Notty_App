package org.tigeryan.notty.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import org.tigeryan.notty.presentation.theme.spacing

data class Action(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
) {

    companion object {
        fun navigateUpAction(title: String, onClick: () -> Unit) = Action(
            title = title,
            onClick = onClick,
            icon = Icons.Default.ArrowBack,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDefaults.backgroundActionBarColors() = smallTopAppBarColors(
    containerColor = MaterialTheme.colorScheme.background,
    scrolledContainerColor = MaterialTheme.colorScheme.background,
    titleContentColor = MaterialTheme.colorScheme.onBackground,
    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
    actionIconContentColor = MaterialTheme.colorScheme.onBackground,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppActionBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationAction: Action? = null,
    actions: List<Action> = emptyList(),
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.backgroundActionBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge
            )
        },
        navigationIcon = {
            navigationAction?.let {
                ActionIcon(action = it)
            }
        },
        actions = {
            actions.forEach { action ->
                ActionIcon(action = action)
            }
        },
        colors = colors,
        windowInsets = windowInsets,
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}

@Composable
private fun ActionIcon(
    action: Action,
) {
    IconButton(
        onClick = action.onClick,
        modifier = Modifier
            .padding(end = MaterialTheme.spacing.small)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surface),
    ) {
        Icon(
            imageVector = action.icon,
            contentDescription = action.title,
        )
    }
}
