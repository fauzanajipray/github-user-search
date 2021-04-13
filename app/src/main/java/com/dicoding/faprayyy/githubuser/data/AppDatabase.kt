package com.dicoding.faprayyy.githubuser.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [UserFavorite::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun userFavDao(): UserFavoriteDao

}