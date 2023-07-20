package org.tigeryan.notty.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.tigeryan.notty.R

@Composable
fun ImageWithTextBelow(
    painter: Painter,
    imageDescription: String?,
    text: String,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Image(
            painter = painter,
            contentDescription = imageDescription,
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyNoteListImageWithTextBelow_Preview() {
    ImageWithTextBelow(
        painter = painterResource(id = R.mipmap.pic_no_notes),
        imageDescription = null,
        text = "Create your first note!",
    )
}

@Preview(showBackground = true)
@Composable
fun NotesNotFoundedImageWithTextBelow_Preview() {
    ImageWithTextBelow(
        painter = painterResource(id = R.mipmap.pic_notes_not_founded),
        imageDescription = null,
        text = "File not found. Try searching again.",
    )
}