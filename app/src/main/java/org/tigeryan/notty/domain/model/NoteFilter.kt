package org.tigeryan.notty.domain.model

import androidx.compose.ui.graphics.Color
import java.util.Locale

fun interface NoteFilter {

    fun filter(note: Note): Boolean
}

class NoteInputFilter(input: String) : NoteFilter {

    val input = input.lowercase(Locale.ROOT)

    override fun filter(note: Note): Boolean {
        if (input.length < 4) return false

        val (title, text) = note.noteData
        return input in title || input in text
    }
}

class NoteColorFilter(private val color: Color) : NoteFilter {

    override fun filter(note: Note): Boolean {
        // TODO add editable colors to notes
        //return note.noteData.color == color
        return false

    }
}