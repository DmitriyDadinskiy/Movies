package com.kotlinmovie.movies.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val NOTE_COLUMN_NOTE = "note"
const val NOTE_COLUMN_ID_FILM = "idFilm"
const val NOTE_TABLE = "notes"

@Entity(tableName = NOTE_TABLE)
data class NoteFilm(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = NOTE_COLUMN_ID_FILM)
    val idFilm: Int,
    @ColumnInfo(name = NOTE_COLUMN_NOTE)
    val note: String,

    )
