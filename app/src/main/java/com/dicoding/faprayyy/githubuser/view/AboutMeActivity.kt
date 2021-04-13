package com.dicoding.faprayyy.githubuser.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.faprayyy.githubuser.databinding.ActivityAboutMeBinding

class AboutMeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutMeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutMeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .load("https://avatars.githubusercontent.com/u/55250402?v=4")
            .into(binding.imageView)
        binding.toolbarId.setNavigationOnClickListener{ this.onBackPressed() }

    }
}