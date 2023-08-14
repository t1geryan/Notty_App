package org.tigeryan.notty.domain.model

enum class AppTheme {
    DAY,
    NIGHT,
    SYSTEM;

    companion object {
        val UNDEFINED: AppTheme get() = SYSTEM
    }
}

enum class NoteSortingStrategy {
    BY_TITLE,
    BY_CREATION_TIME,
    BY_MODIFICATION_TIME;

    fun toNoteComparator() = when (this) {
        BY_TITLE -> NoteTitleComparator
        BY_CREATION_TIME -> NoteCreationTimeComparator
        BY_MODIFICATION_TIME -> NoteModificationTimeComparator
    }
}