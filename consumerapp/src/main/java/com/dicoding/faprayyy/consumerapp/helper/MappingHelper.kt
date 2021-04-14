package com.dicoding.faprayyy.consumerapp.helper

import android.database.Cursor
import android.os.Parcelable
import com.dicoding.faprayyy.consumerapp.data.UserFavorite
import com.dicoding.faprayyy.consumerapp.dataModel.UserModel

object MappingHelper {
    fun mapCursorToArrayList(favoritesCursor: Cursor?): ArrayList<UserFavorite> {
        val favoriteList = ArrayList<UserFavorite>()

        favoritesCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow("username"))
                val name = getString(getColumnIndexOrThrow("name"))
                val avatar = getString(getColumnIndexOrThrow("avatar"))
                val bio = getString(getColumnIndexOrThrow("bio"))
                val company = getString(getColumnIndexOrThrow("company"))
                val location = getString(getColumnIndexOrThrow("location"))
                val repository = getInt(getColumnIndexOrThrow("repository"))
                val follower = getInt(getColumnIndexOrThrow("follower"))
                val following = getInt(getColumnIndexOrThrow("following"))
                favoriteList.add(
                    UserFavorite(
                        username, name, avatar, bio, company, location, repository, follower, following
                    )
                )
            }
        }

        return favoriteList
    }


    fun mapCursorToObject(notesCursor: Cursor?): UserFavorite {
        var user = UserFavorite()
        notesCursor?.apply {
            moveToFirst()
            val username = getString(getColumnIndexOrThrow("username"))
            val name = getString(getColumnIndexOrThrow("name"))
            val avatar = getString(getColumnIndexOrThrow("avatar"))
            val bio = getString(getColumnIndexOrThrow("bio"))
            val company = getString(getColumnIndexOrThrow("company"))
            val location = getString(getColumnIndexOrThrow("location"))
            val repository = getInt(getColumnIndexOrThrow("repository"))
            val follower = getInt(getColumnIndexOrThrow("follower"))
            val following = getInt(getColumnIndexOrThrow("following"))
            user = UserFavorite(
                    username, name, avatar, bio, company, location, repository, follower, following
            )
        }
        return user
    }
}