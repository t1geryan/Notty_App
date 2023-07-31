package org.tigeryan.notty.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


@Composable
fun <T> rememberMutableStateOf(
    value: T,
): MutableState<T> {
    return remember {
        mutableStateOf(value)
    }
}