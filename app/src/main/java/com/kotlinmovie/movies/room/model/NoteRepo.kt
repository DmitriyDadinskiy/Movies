package com.kotlinmovie.movies.room.model

interface NoteRepo {

    fun add(note: NoteFilm): Int
    fun delete(noteFilm: List<NoteFilm>)
    fun getNotesIdFilm(notesId: Int): NoteFilm
    fun exists(id: Int): Boolean
    fun getNotesListFilm(notesId: Int): List<NoteFilm>
    fun updateNoteFilm(noteFilm: NoteFilm)
}
