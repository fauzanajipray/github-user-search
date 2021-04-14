package com.dicoding.faprayyy.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserFavoriteRepository(private val userFavoriteDao: UserFavoriteDao) {

    val readAllData : LiveData<List<UserFavorite>> = userFavoriteDao.readAllData()
    val readData = MutableLiveData<UserFavorite>()

//    //            = userFavoriteDao.getUserByID()
    suspend fun addUserFavorite(user: UserFavorite){
        userFavoriteDao.addUserFavorite(user)
    }

//    suspend fun getUserFavorite(userName : String){
//        readData.postValue(userFavoriteDao.getUserByID(userName))
//    }
//
//    fun getUserFavByID(): LiveData<UserFavorite>{
//        return readData
//    }

}