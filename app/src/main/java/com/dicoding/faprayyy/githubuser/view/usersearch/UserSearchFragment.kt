package com.dicoding.faprayyy.githubuser.view.usersearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.faprayyy.githubuser.R
import com.dicoding.faprayyy.githubuser.adapter.UserAdapter
import com.dicoding.faprayyy.githubuser.databinding.FragmentUserSearchBinding
import com.dicoding.faprayyy.githubuser.datamodel.UserModel
import com.dicoding.faprayyy.githubuser.view.settings.SettingsActivity

class UserSearchFragment : Fragment() {

    companion object{
        var stateTvSearchMsg = true
    }

    private var _binding: FragmentUserSearchBinding? = null
    private val binding get() = _binding as FragmentUserSearchBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: UserSearchViewModel
    val emptyList = ArrayList<UserModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        setUpToolbar()
        showTvSearchFirst(stateTvSearchMsg)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        UserSearchViewModel.notFoundText = resources.getString(R.string.user_not_found)
        viewModel = ViewModelProvider(this).get(UserSearchViewModel::class.java)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvUser.layoutManager = LinearLayoutManager(activity)
        binding.rvUser.adapter = adapter
        searchData()
        if (adapter.mData != ArrayList<UserModel>()){
            showTvSearchFirst(false)
        }
        viewModel.getUsers().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setData(it)
                showLoading(false)
            }
        })

        viewModel.getStateSearch().observe(viewLifecycleOwner){ state ->
            if (!state){
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
        val tv = binding.tvSearchFirst
        if (state) tv.visibility = View.VISIBLE else tv.visibility = View.GONE
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
                    showTvSearchFirst(true)
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
                        R.id.menu_item_favorite -> findNavController().navigate(UserSearchFragmentDirections.actionUserSearchFragmentToFavoriteUserFragment())
                        R.id.menu_item_settings -> {
                            val mIntent = Intent(activity, SettingsActivity::class.java)
                            startActivity(mIntent)
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