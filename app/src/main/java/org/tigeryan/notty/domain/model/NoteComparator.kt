package org.tigeryan.notty.domain.model

fun interface NoteComparator : Comparator<Note> {

    override fun compare(note1: Note, note2: Note): Int
}

object NoteModificationTimeComparator : NoteComparator {

    override fun compare(note1: Note, note2: Note): Int =
        note1.noteTime.modifiedAt.compareTo(note2.noteTime.modifiedAt)
}

object NoteCreationTimeComparator : NoteComparator {

    override fun compare(note1: Note, note2: Note): Int =
        note1.noteTime.createdAt.compareTo(note2.noteTime.createdAt)
}

object NoteTitleComparator : NoteComparator {

    override fun compare(note1: Note, note2: Note): Int =
        note1.noteData.title.compareTo(note2.noteData.title, ignoreCase = true)
}