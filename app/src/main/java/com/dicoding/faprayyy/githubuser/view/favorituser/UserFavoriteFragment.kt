package com.dicoding.faprayyy.githubuser.view.favorituser

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.faprayyy.githubuser.adapter.UserFavoriteAdapter
import com.dicoding.faprayyy.githubuser.databinding.FragmentFavoriteUserBinding

class UserFavoriteFragment : Fragment() {

    private lateinit var viewModel: UserFavoriteViewModel
    private var _binding: FragmentFavoriteUserBinding? = null
    private val binding get() = _binding as FragmentFavoriteUserBinding
    private lateinit var adapter: UserFavoriteAdapter
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteUserBinding.inflate(inflater, container, false)
        val view = binding.root
        showLoading()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserFavoriteViewModel::class.java)

        adapter = UserFavoriteAdapter()
        adapter.notifyDataSetChanged()

    }

    private fun showLoading() {

    }


}