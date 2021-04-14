package com.dicoding.faprayyy.githubuser.view.userdetail

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class UserDetailViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext


}