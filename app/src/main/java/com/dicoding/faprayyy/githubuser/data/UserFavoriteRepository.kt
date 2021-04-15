package com.dicoding.faprayyy.githubuser.data

import androidx.lifecycle.LiveData

class UserFavoriteRepository(private val userDao: UserFavoriteDao) {

    val readAllData: LiveData<List<UserFavorite>> = userDao.readAllData()

    suspend fun addUser(user: UserFavorite){
        userDao.addUserFavorite(user)
    }

    suspend fun deleteUser(user: UserFavorite){
        userDao.deleteUser(user)
    }

    suspend fun readUserById(userName : String) : UserFavorite{
        return userDao.readUserById(userName)
    }

}