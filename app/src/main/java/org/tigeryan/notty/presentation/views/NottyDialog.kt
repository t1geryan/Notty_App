package org.tigeryan.notty.presentation.views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogProperties
import org.tigeryan.notty.utils.extensions.rememberMutableStateOf

@Composable
fun NottyDialog(
    title: String,
    modifier: Modifier = Modifier,
    message: String? = null,
    confirmButtonText: String? = null,
    cancelButtonText: String? = null,
    isDismissible: Boolean = true,
    dialogColors: DialogColors = DialogColors.getDefaultDialogColors(),
    shape: Shape = AlertDialogDefaults.shape,
    tonalElevation: Dp = AlertDialogDefaults.TonalElevation,
    properties: DialogProperties = DialogProperties(),
    callback: DialogCallback? = null,
) {
    var shouldShow by rememberMutableStateOf(value = true)
    if (shouldShow) {
        AlertDialog(
            onDismissRequest = {
                callback?.let {
                    it.handle(DialogCallbackTypes.DISMISS)
                    if (isDismissible)
                        shouldShow = false
                }
            },
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            text = {
                message?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            },
            confirmButton = {
                confirmButtonText?.let {
                    Button(
                        onClick = {
                            callback?.let {
                                it.handle(DialogCallbackTypes.CONFIRM)
                                shouldShow = false
                            }
                        }
                    ) {
                        Text(text = it)
                    }
                }
            },
            dismissButton = {
                cancelButtonText?.let {
                    Button(
                        onClick = {
                            callback?.let {
                                it.handle(DialogCallbackTypes.CANCEL)
                                shouldShow = false
                            }
                        }
                    ) {
                        Text(text = it)
                    }
                }
            },
            shape = shape,
            containerColor = dialogColors.containerColor,
            titleContentColor = dialogColors.titleContentColor,
            textContentColor = dialogColors.textContentColor,
            tonalElevation = tonalElevation,
            properties = properties,
            modifier = modifier,
        )
    }
}

fun interface DialogCallback {

    fun handle(what: DialogCallbackTypes)
}

enum class DialogCallbackTypes {
    CONFIRM,
    CANCEL,
    DISMISS,
}

@Immutable
data class DialogColors(
    val containerColor: Color,
    val titleContentColor: Color,
    val textContentColor: Color,
) {

    companion object {
        @Composable
        fun getDefaultDialogColors() = DialogColors(
            containerColor = AlertDialogDefaults.containerColor,
            titleContentColor = AlertDialogDefaults.titleContentColor,
            textContentColor = AlertDialogDefaults.textContentColor,
        )
    }
}