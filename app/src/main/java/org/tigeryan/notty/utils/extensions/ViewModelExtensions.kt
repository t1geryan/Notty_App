package org.tigeryan.notty.utils.extensions

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

val ViewModel.viewModelScopeIO
    get() = CoroutineScope(SupervisorJob() + Dispatchers.IO)

/**
 * wrapper for outputting errors when emit Flow or set StateFlow value
 * @param emitBlock block of code where emitting Flow or setting StateFlow value
 */
fun ViewModel.tryEmitFlow(
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