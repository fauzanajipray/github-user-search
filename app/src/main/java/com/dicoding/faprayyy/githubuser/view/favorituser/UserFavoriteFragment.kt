package com.dicoding.faprayyy.githubuser.view.favorituser

import android.content.Context
import android.opengl.Visibility
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.faprayyy.githubuser.adapter.UserAdapter
import com.dicoding.faprayyy.githubuser.adapter.UserFavoriteAdapter
import com.dicoding.faprayyy.githubuser.data.UserFavorite
import com.dicoding.faprayyy.githubuser.databinding.FragmentFavoriteUserBinding
import com.dicoding.faprayyy.githubuser.datamodel.UserModel
import com.dicoding.faprayyy.githubuser.view.usersearch.UserSearchFragmentDirections

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
        showWarningTv(true)
        return view
    }

    private fun showWarningTv(state: Boolean) {
        if (state){
            binding.tvWarning.visibility = View.VISIBLE
        } else
            binding.tvWarning.visibility = View.GONE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserFavoriteViewModel::class.java)

        adapter = UserFavoriteAdapter()
        adapter.notifyDataSetChanged()
        binding.rvUserFavorite.layoutManager = LinearLayoutManager(activity)
        binding.rvUserFavorite.adapter = adapter

        viewModel.readAllData.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setData(it)
            } else {
                showWarningTv(true)
            }
        }

        adapter.setOnItemClickCallback(object : UserFavoriteAdapter.OnItemClickCallback {

            override fun onItemClicked(data: UserFavorite) {
                val dataUser = data.let { UserModel(it.username, it.name, it.avatar, it.bio, it.company,
                            it.location, it.repository, it.follower, it.following) }

                dataUser.apply {
                    name = data.name
                    username = data.username
                    avatar = data.avatar
                    bio = data.bio
                    company = data.company
                    follower = data.follower
                    following = data.following
                    location = data.location
                    repository = data.repository
                }
                val actionTo = UserFavoriteFragmentDirections.actionFavoriteUserFragmentToDetailUserFragment(dataUser)
                findNavController().navigate(actionTo)
            }
        })

    }

}