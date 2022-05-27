package com.kotlinmovie.movies.room.impl

import androidx.room.*
import com.kotlinmovie.movies.room.model.NoteFilm

@Dao
interface NoteDao {
    @Insert
    fun add(noteFilm: NoteFilm)

    @Delete
    fun delete(noteFilm: List<NoteFilm>)

    @Query("SELECT EXISTS (SELECT 1 FROM notes WHERE idFilm = :id)")
    fun exists(id: Int): Boolean

    @Query("SELECT * FROM notes WHERE idFilm = :notesId")
    fun getNotesIdFilm(notesId: Int): NoteFilm

    @Query("SELECT * FROM notes WHERE idFilm = :notesId")
    fun getNotesListFilm(notesId: Int): List<NoteFilm>

    @Update
    fun updateNoteFilm(noteFilm: NoteFilm)
}