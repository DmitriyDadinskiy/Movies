package com.kotlinmovie.movies.room.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kotlinmovie.movies.room.model.NoteFilm

@Database(
    entities = [NoteFilm::class],
    version = 1
)
abstract class NoteRoomDb : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}