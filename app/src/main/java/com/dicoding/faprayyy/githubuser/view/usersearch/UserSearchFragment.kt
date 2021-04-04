package com.dicoding.faprayyy.githubuser.view.usersearch

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.faprayyy.githubuser.R
import com.dicoding.faprayyy.githubuser.adapter.UserAdapter
import com.dicoding.faprayyy.githubuser.databinding.UserSearchFragmentBinding
import com.dicoding.faprayyy.githubuser.datamodel.UserModel

class UserSearchFragment : Fragment() {

    companion object {
        var stateTvSearchMsg = true
        var stateUserNotFound = false
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

        setUpToolbar()
        showTvSearchFirst(stateTvSearchMsg)

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
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        }

        viewModel.getStateSearch().observe(viewLifecycleOwner){ state ->
            if (!state){
                stateUserNotFound = true
                showLoading(false)
                showTvSearchFirst(true)
            }
        }

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserModel) {
                val actionTo =
                    UserSearchFragmentDirections.actionUserSearchFragmentToDetailUserFragment(
                        data
                    )
                findNavController().navigate(actionTo)
            }
        })
    }

    fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showTvSearchFirst(state: Boolean){
        val userNotFoundMsg = resources.getString(R.string.user_not_found)
        val searchFirstMsg = resources.getString(R.string.please_search_first)
        val tv = binding.tvSearchFirst
        if (state) tv.visibility = View.VISIBLE else tv.visibility = View.GONE
        if (stateUserNotFound) tv.text = userNotFoundMsg  else tv.text = searchFirstMsg

    }

    private fun searchData() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return false
                } else {
                    adapter.setData(emptyList)
                    showTvSearchFirst(false)
                    showLoading(true)
                    viewModel.setUser(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    adapter.setData(emptyList)
                    showLoading(false)
                    stateUserNotFound = false
                    showTvSearchFirst(stateTvSearchMsg)
                }
                return true
            }
        })
    }

    private fun setUpToolbar(){
        binding.toolbarId.apply {
            setTitle(R.string.app_name)
            inflateMenu(R.menu.main_menu)
            setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener{
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when(item?.itemId){
                        R.id.menu_item_about -> {
                            findNavController().navigate(UserSearchFragmentDirections.actionUserSearchFragmentToAboutMeFragment())
                        }
                    }
                    return true
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}