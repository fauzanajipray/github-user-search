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

    private val readAllData: LiveData<List<UserFavorite>>
    private val repository: UserFavoriteRepository
    private val userById = MutableLiveData<UserFavorite?>()

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

    fun deleteUser(user: UserFavorite){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
        userById.postValue(null)
    }

    fun readUserById(userName : String){
        viewModelScope.launch(Dispatchers.IO) {
            userById.postValue(repository.readUserById(userName))
        }
    }

    fun getUserById() : LiveData<UserFavorite?>{
        return userById
    }
}