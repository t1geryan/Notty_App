package org.tigeryan.notty.presentation.screens.settings.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import org.tigeryan.notty.presentation.theme.NottyTheme
import org.tigeryan.notty.presentation.theme.spacing
import org.tigeryan.notty.utils.extensions.rememberMutableStateOf

data class SettingDropdownMenuModel(
    val title: String,
    val currentIndex: Int = 0,
    val values: List<String>
)

@Composable
internal fun SettingDropdownMenu(
    model: SettingDropdownMenuModel,
    modifier: Modifier = Modifier,
    onItemSelected: ((Int) -> Unit)? = null,
) {
    var isDropdownOpen by rememberMutableStateOf(value = false)
    var currentPosition by rememberMutableStateOf(value = model.currentIndex)

    val contentColor = colorScheme.onBackground
    val contentColorAlpha = remember(contentColor) {
        contentColor.copy(alpha = 0.4f)
    }
    val containerColor = colorScheme.background

    Column(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .background(containerColor)
                .fillMaxWidth()
        ) {
            Row(
                Modifier
                    .clickable {
                        isDropdownOpen = true
                    }
                    .padding(MaterialTheme.spacing.medium),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = MaterialTheme.spacing.medium),
                    text = model.title,
                    style = typography.bodyLarge,
                    color = contentColor,
                )

                Text(
                    text = model.values[currentPosition],
                    style = typography.bodyLarge,
                    color = contentColorAlpha,
                )

                IconButton(
                    onClick = { isDropdownOpen = !isDropdownOpen },
                ) {
                    Icon(
                        imageVector = if (isDropdownOpen) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                        contentDescription = null,
                        tint = contentColorAlpha,
                    )
                }

            }
        }

        Divider(
            thickness = Dp.Hairline,
            color = contentColorAlpha,
        )

        DropdownMenu(
            expanded = isDropdownOpen,
            onDismissRequest = {
                isDropdownOpen = false
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(colorScheme.background)
        ) {
            model.values.forEachIndexed { index, value ->
                DropdownMenuItem(onClick = {
                    currentPosition = index
                    isDropdownOpen = false
                    onItemSelected?.invoke(index)
                }) {
                    Text(
                        text = value,
                        style = typography.bodyLarge,
                        color = colorScheme.onBackground,
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SettingDropdownMenuDarkPreview() {
    NottyTheme(
        darkTheme = true
    ) {
        SettingDropdownMenu(
            model = SettingDropdownMenuModel(
                title = "App Theme",
                values = listOf(
                    "System Theme",
                    "Night Theme",
                    "Day Theme",
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SettingDropdownMenuLightPreview() {
    NottyTheme(
        darkTheme = false,
    ) {
        SettingDropdownMenu(
            model = SettingDropdownMenuModel(
                title = "App Theme",
                values = listOf(
                    "System Theme",
                    "Night Theme",
                    "Day Theme",
                )
            )
        )
    }
}