package com.dicoding.faprayyy.githubuser.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserFavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserFavorite(user: UserFavorite)

    @Update
    suspend fun updateUser(user: UserFavorite)

    @Delete
    suspend fun deleteUser(user: UserFavorite)

    @Query("DELETE FROM favorite")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM favorite WHERE username = :userName")
    suspend fun deleteUserById(userName: String)

    @Query("SELECT * FROM favorite ORDER BY username ASC")
    fun readAllData(): LiveData<List<UserFavorite>>

    @Query("SELECT * FROM favorite WHERE username = :userName")
    suspend fun readUserById(userName : String): UserFavorite

    /**
     * Cursor for content provider
     */
    @Query("SELECT * FROM favorite")
    fun cursorGetAllUserFavorite() : Cursor

    @Query("SELECT * FROM favorite WHERE username = :userName")
    fun cursorGetUserByID(userName: String): Cursor?
}