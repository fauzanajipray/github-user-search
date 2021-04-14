package com.dicoding.faprayyy.consumerapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserFavorite(
    var username: String? = "",
    var name: String? = "",
    var avatar: String? = "",
    var bio: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: Int = 0,
    var follower: Int = 0,
    var following: Int = 0
) : Parcelable

