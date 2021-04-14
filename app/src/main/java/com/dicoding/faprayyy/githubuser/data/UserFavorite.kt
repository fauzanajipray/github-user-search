package com.dicoding.faprayyy.githubuser.data

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "favorite")
data class UserFavorite(
    @PrimaryKey
    @NonNull
    var username: String,
    var name: String?,
    var avatar: String?,
    var bio: String?,
    var company: String?,
    var location: String?,
    var repository: Int,
    var follower: Int,
    var following: Int
) : Parcelable

