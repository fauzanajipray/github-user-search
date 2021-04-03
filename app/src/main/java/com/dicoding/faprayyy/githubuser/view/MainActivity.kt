package com.dicoding.faprayyy.githubuser.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.dicoding.faprayyy.githubuser.R
import com.dicoding.faprayyy.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)

    }
}