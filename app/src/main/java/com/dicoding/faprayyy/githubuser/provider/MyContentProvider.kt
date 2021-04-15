package com.dicoding.faprayyy.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dicoding.faprayyy.githubuser.data.UserFavoriteDatabase

class MyContentProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.dicoding.faprayyy.githubuser"
        private const val FAVORITE = 1
        private const val FAVORITE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private const val TABLE_NAME = "favorite"
        init {
            // content://com.dicoding.faprayyy.githubuser/favorite
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)
            // content://com.dicoding.faprayyy.githubuser/favorite/id
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", FAVORITE_ID)
        }
    }
    private var database: UserFavoriteDatabase? = null

    override fun onCreate(): Boolean {
        database = UserFavoriteDatabase.getDatabase(context as Context)
        return (database != null)
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                              selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAVORITE -> database?.userFavDao()?.cursorGetAllUserFavorite()
            FAVORITE_ID -> database?.userFavDao()?.cursorGetUserByID(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                               selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

}