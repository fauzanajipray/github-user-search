package com.dicoding.faprayyy.githubuser.view.favorituser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.faprayyy.githubuser.data.UserFavorite
import com.dicoding.faprayyy.githubuser.data.UserFavoriteDatabase
import com.dicoding.faprayyy.githubuser.data.UserFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserFavoriteViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<UserFavorite>>
    private val repository: UserFavoriteRepository
    val userById = MutableLiveData<UserFavorite?>()

    init {
        val userDao = UserFavoriteDatabase.getDatabase(
                application
        ).userFavDao()
        repository = UserFavoriteRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: UserFavorite){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun updateUser(user: UserFavorite){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: UserFavorite){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
        userById.postValue(null)
    }

    fun deleteAllUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }

    fun readUserById(userName : String){
        viewModelScope.launch(Dispatchers.IO) {
            userById.postValue(repository.readUserById(userName))
        }
    }

    fun deleteUserById(userName: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserById(userName)
        }
    }


    fun getUserById() : LiveData<UserFavorite?>{
        return userById
    }
}