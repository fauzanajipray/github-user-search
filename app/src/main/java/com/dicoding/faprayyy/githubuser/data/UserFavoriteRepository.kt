package com.dicoding.faprayyy.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserFavoriteRepository(private val userDao: UserFavoriteDao) {

    val readAllData: LiveData<List<UserFavorite>> = userDao.readAllData()

    suspend fun addUser(user: UserFavorite){
        userDao.addUserFavorite(user)
    }

    suspend fun updateUser(user: UserFavorite){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: UserFavorite){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }

    suspend fun readUserById(userName : String) : UserFavorite{
        return userDao.readUserById(userName)
    }

    suspend fun deleteUserById(userName : String){
        userDao.readUserById(userName)
    }


}