package org.tigeryan.notty.utils.extensions

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * wrapper for outputting errors when emit Flow or set StateFlow value
 * @param emitBlock block of code where emitting Flow or setting StateFlow value
 */
fun tryEmitFlow(
    coroutineScope: CoroutineScope,
    emitBlock: suspend () -> Unit,
) {
    coroutineScope.launch {
        try {
            emitBlock()
        } catch (e: Exception) {
            Log.w("Flow Emit Error", "Exception $e caught when emit flow")
            e.printStackTrace()
        }
    }
}

/**
 * Wrapper for calling withContext([Dispatchers.IO])
 */
suspend fun <T> withIOContext(block: suspend CoroutineScope.() -> T) =
    withContext(Dispatchers.IO, block)