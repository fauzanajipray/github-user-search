package com.dicoding.faprayyy.githubuser.datamodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var username: String? = "",
    var name: String? = "",
    var avatar: String? = "",
    var bio: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: Int = 0,
    var follower: Int = 0,
    var following: Int = 0
): Parcelable
