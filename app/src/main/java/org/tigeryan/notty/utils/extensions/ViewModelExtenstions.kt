package org.tigeryan.notty.utils.extensions

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

val ViewModel.viewModelScopeIO
    get() = CoroutineScope(SupervisorJob() + Dispatchers.IO)

