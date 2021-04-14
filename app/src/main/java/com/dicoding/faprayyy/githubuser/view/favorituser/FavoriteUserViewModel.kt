package com.dicoding.faprayyy.githubuser.view.favorituser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.faprayyy.githubuser.BuildConfig
import com.dicoding.faprayyy.githubuser.data.UserFavorite
import com.dicoding.faprayyy.githubuser.data.UserFavoriteDatabase
import com.dicoding.faprayyy.githubuser.data.UserFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteUserViewModel(application: Application) : AndroidViewModel(application) {

    private val readAllData : LiveData<List<UserFavorite>>
    private var repository : UserFavoriteRepository

    val apikey = BuildConfig.GITHUB_TOKEN

    init {
        val userDao = UserFavoriteDatabase.getDatabase(application).userFavDao()
        repository = UserFavoriteRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: UserFavorite){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserFavorite(user)
        }
    }
}