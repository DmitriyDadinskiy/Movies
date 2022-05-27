package com.kotlinmovie.movies.room.impl

import android.content.Context
import androidx.room.Room
import com.kotlinmovie.movies.room.model.NoteFilm
import com.kotlinmovie.movies.room.model.NoteRepo

class RoomNoteRepoImpl(context: Context) : NoteRepo {
    private val noteDao: NoteDao =
        Room.databaseBuilder(context,
            NoteRoomDb::class.java, "notes.bd")
            .build().noteDao()


    override fun add(note: NoteFilm): Int {
        noteDao.add(note)
        return -1
    }

    override fun delete(noteFilm: List<NoteFilm>) {
        return noteDao.delete(noteFilm)
    }

    override fun getNotesIdFilm(notesId: Int): NoteFilm {
        return noteDao.getNotesIdFilm(notesId)
    }

    override fun exists(id: Int): Boolean {
        return noteDao.exists(id)
    }

    override fun getNotesListFilm(notesId: Int): List<NoteFilm> {
        return noteDao.getNotesListFilm(notesId)
    }

    override fun updateNoteFilm(noteFilm: NoteFilm) {
        return noteDao.updateNoteFilm(noteFilm)
    }

}
