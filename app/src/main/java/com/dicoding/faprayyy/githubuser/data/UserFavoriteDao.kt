package com.dicoding.faprayyy.githubuser.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserFavoriteDao {
    @Query("SELECT * FROM favorite")
    fun readAllData() : LiveData<List<UserFavorite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserFavorite(user: UserFavorite)


    /**
     * Cursor for content provider
     */
    @Query("SELECT * FROM favorite")
    fun cursorGetAllUserFavorite() : Cursor

    @Delete
    fun deleteUserFromFavoriteDB(user: UserFavorite)

    @Query("SELECT * FROM favorite WHERE username = :userName")
    fun cursorGetUserByID(userName : String) : Cursor
}