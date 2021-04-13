package com.dicoding.faprayyy.githubuser.view.favorituser

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dicoding.faprayyy.githubuser.R
import com.dicoding.faprayyy.githubuser.databinding.FavoriteUserFragmentBinding
import com.dicoding.faprayyy.githubuser.view.alarm.AlarmReceiver

class FavoriteUserFragment : Fragment() {

    private lateinit var viewModel: FavoriteUserViewModel
    private var _binding: FavoriteUserFragmentBinding? = null
    private val binding get() = _binding as FavoriteUserFragmentBinding
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoriteUserFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        showLoading()
        return view
    }

    private fun showLoading() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteUserViewModel::class.java)

    }


}