package org.tigeryan.notty.domain.model

enum class AppTheme {
    DAY,
    NIGHT,
    SYSTEM;

    companion object {
        val UNDEFINED: AppTheme get() = SYSTEM
    }
}