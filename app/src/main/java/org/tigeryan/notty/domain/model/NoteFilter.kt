package org.tigeryan.notty.domain.model

fun interface Filter<T> {

    operator fun invoke(value: T): Boolean
}

fun interface NoteFilter : Filter<Note>

class NoteInputFilter(private val input: String) : NoteFilter {

    override operator fun invoke(value: Note): Boolean {
        if (input.isEmpty()) return false

        val (title, text) = value.noteData
        return title.contains(input, ignoreCase = true) || text.contains(input, ignoreCase = true)
    }
}

fun <T> Iterable<T>.filter(filter: Filter<T>): List<T> =
    filter(
        predicate = {
            filter(it)
        }
    )