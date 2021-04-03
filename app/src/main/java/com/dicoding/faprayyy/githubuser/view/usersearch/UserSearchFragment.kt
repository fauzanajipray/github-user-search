package com.dicoding.faprayyy.githubuser.view.usersearch

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.faprayyy.githubuser.R
import com.dicoding.faprayyy.githubuser.adapter.UserAdapter
import com.dicoding.faprayyy.githubuser.databinding.UserSearchFragmentBinding
import com.dicoding.faprayyy.githubuser.datamodel.UserModel

class UserSearchFragment : Fragment() {

    companion object {
        val TAG = UserSearchFragment::class.java.simpleName
    }

    private var _binding: UserSearchFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UserAdapter

    private lateinit var viewModel: UserSearchViewModel
    val emptyList = ArrayList<UserModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserSearchFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserSearchViewModel::class.java)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvUser.layoutManager = LinearLayoutManager(activity)
        binding.rvUser.adapter = adapter

        searchData()

        viewModel.getUsers().observe(viewLifecycleOwner) { userItems ->
            Log.d("CEK _$TAG", userItems.toString())
            if (userItems != null) {
                Log.d("CEK $TAG", userItems.toString())
                adapter.setData(userItems)
                showLoading(false)
            }
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun searchData() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return false
                } else {

                    showLoading(true)
                    viewModel.setUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    adapter.setData(emptyList)
                }
                return true
            }
        })
    }

}