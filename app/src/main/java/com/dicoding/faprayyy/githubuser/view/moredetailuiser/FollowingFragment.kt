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
import com.dicoding.faprayyy.githubuser.databinding.FragmentFollowingBinding
import com.dicoding.faprayyy.githubuser.datamodel.UserModel

class FollowingFragment : Fragment() {

    companion object{
        val TAG = FollowerFragment::class.java.simpleName
    }

    private lateinit var viewModel: FollowingViewModel
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var FollowingAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root
        showLoading(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)
        FollowingAdapter = UserAdapter()
        FollowingAdapter.notifyDataSetChanged()
        binding.rvFollowing.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = FollowingAdapter
        }

        Log.d(TAG,  FollowerFollowingFragment.EXTRA_USERNAME)
        viewModel.setFollowing( FollowerFollowingFragment.EXTRA_USERNAME)
        viewModel.getFollowing().observe(viewLifecycleOwner) { followingList ->
            if (followingList != null) {
                FollowingAdapter.setData(followingList)
                showLoading(false)
            }
        }

        FollowingAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
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