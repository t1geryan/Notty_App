package org.tigeryan.notty.domain.model

import androidx.compose.ui.graphics.Color

fun interface NoteFilter {

    operator fun invoke(note: Note): Boolean
}

class NoteInputFilter(private val input: String) : NoteFilter {

    override operator fun invoke(note: Note): Boolean {
        if (input.isEmpty()) return false

        val (title, text) = note.noteData
        return title.contains(input, ignoreCase = true) || text.contains(input, ignoreCase = true)
    }
}

class NoteColorFilter(private val color: Color) : NoteFilter {

    override operator fun invoke(note: Note): Boolean {
        // TODO add editable colors to notes
        //return note.noteData.color == color
        return false

    }
}