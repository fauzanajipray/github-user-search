package com.dicoding.faprayyy.githubuser.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [UserFavorite::class], version = 1, exportSchema = false)
abstract class UserFavoriteDatabase : RoomDatabase() {

    abstract fun userFavDao(): UserFavoriteDao
//    abstract fun userFavRepo(): UserFavoriteRepository

    companion object{
        @Volatile
        private var INSTANCE : UserFavoriteDatabase? = null
        private val  DATABASE_NAME = "user_favorite_database"

        fun getDatabase(context: Context) : UserFavoriteDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return  tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserFavoriteDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}