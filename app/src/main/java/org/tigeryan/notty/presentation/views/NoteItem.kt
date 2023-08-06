package org.tigeryan.notty.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import org.tigeryan.notty.domain.model.Note
import org.tigeryan.notty.presentation.theme.dimens
import org.tigeryan.notty.presentation.theme.spacing

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        shape = MaterialTheme.shapes.large,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
        ) {
            Text(
                text = note.noteData.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = MaterialTheme.dimens.singleTextLinesCount,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = note.noteData.text,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = MaterialTheme.dimens.smallTextLinesCount,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}