package org.tigeryan.notty.presentation.screens.settings.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
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
import org.tigeryan.notty.presentation.theme.dimens
import org.tigeryan.notty.presentation.theme.spacing
import org.tigeryan.notty.utils.extensions.rememberMutableStateOf

data class ToggleSettingModel(
    val title: String,
    val isChecked: Boolean,
)

@Composable
fun ToggleSetting(
    model: ToggleSettingModel,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    val contentColor = MaterialTheme.colorScheme.onBackground
    val contentColorAlpha = remember(contentColor) {
        contentColor.copy(alpha = 0.4f)
    }
    val containerColor = MaterialTheme.colorScheme.background

    var isChecked by rememberMutableStateOf(value = model.isChecked)

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .background(containerColor)
                .fillMaxWidth()
                .requiredHeightIn(min = MaterialTheme.dimens.minClickableSize)
                .padding(
                    horizontal = MaterialTheme.spacing.medium,
                    vertical = MaterialTheme.spacing.small,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = model.title,
                style = MaterialTheme.typography.bodyLarge,
                color = contentColor,
            )
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = !isChecked
                    onCheckedChange(isChecked)
                }
            )
        }
        Divider(
            thickness = Dp.Hairline,
            color = contentColorAlpha,
        )
    }
}


@Composable
@Preview(showBackground = true)
fun ToggleSettingDarkPreview() {
    NottyTheme(
        darkTheme = true
    ) {
        ToggleSetting(
            model = ToggleSettingModel(
                title = "Descending sort",
                isChecked = true,
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ToggleSettingLightPreview() {
    NottyTheme(
        darkTheme = false,
    ) {
        ToggleSetting(
            model = ToggleSettingModel(
                title = "Descending sort",
                isChecked = true,
            )
        )
    }
}