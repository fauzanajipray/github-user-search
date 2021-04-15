package com.dicoding.faprayyy.githubuser.view.moredetailuiser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.faprayyy.githubuser.adapter.UserAdapter
import com.dicoding.faprayyy.githubuser.databinding.FragmentFollowerBinding
import com.dicoding.faprayyy.githubuser.datamodel.UserModel

class FollowerFragment : Fragment() {

    private lateinit var viewModel: FollowerViewModel
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding as FragmentFollowerBinding
    private lateinit var followerAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        val view = binding.root
        showLoading(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FollowerViewModel::class.java)
        followerAdapter = UserAdapter()
        followerAdapter.notifyDataSetChanged()
        binding.rvFollower.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = followerAdapter
        }

        viewModel.setFollower(FollowerFollowingFragment.EXTRA_USERNAME)
        viewModel.getFollower().observe(viewLifecycleOwner) { followersList ->
            Log.d("TAG", followersList.toString())

            if (followersList != null) {
                followerAdapter.setData(followersList)
                showLoading(false)
            }
        }
        viewModel.getStateSearch().observe(viewLifecycleOwner){ state ->
            if (!state){
                showLoading(false)
            }
        }

        followerAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserModel) {
                val actionTo =
                    FollowerFollowingFragmentDirections
                        .actionFollowerFollowingFragmentToDetailUserFragment(data)
                findNavController().navigate(actionTo)
            }
        })

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}