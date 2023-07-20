package org.tigeryan.notty.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import org.tigeryan.notty.presentation.theme.spacing

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeBackground(
    dismissState: DismissState,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    shape: Shape? = null,
    directions: Set<DismissDirection> = setOf(
        DismissDirection.EndToStart,
        DismissDirection.StartToEnd
    ),
) {
    val direction = dismissState.dismissDirection ?: return
    if (direction !in directions) return

    val alignment = when (direction) {
        DismissDirection.StartToEnd -> Alignment.CenterStart
        DismissDirection.EndToStart -> Alignment.CenterEnd
    }

    Box(
        modifier = modifier
            .clip(shape ?: RectangleShape)
            .background(color),
        contentAlignment = alignment
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(end = MaterialTheme.spacing.small)
        )
    }
}