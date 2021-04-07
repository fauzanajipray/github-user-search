package com.dicoding.faprayyy.githubuser.view.userdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.faprayyy.githubuser.R
import com.dicoding.faprayyy.githubuser.databinding.DetailUserFragmentBinding
import com.dicoding.faprayyy.githubuser.utils.convertNullToString
import com.dicoding.faprayyy.githubuser.view.usersearch.UserSearchFragment

class DetailUserFragment : Fragment() {

    companion object {
        val TAG = UserSearchFragment::class.java.simpleName
    }

    private var _binding: DetailUserFragmentBinding? = null
    private val binding get() = _binding as DetailUserFragmentBinding

    private val args by navArgs<DetailUserFragmentArgs>()

    @SuppressLint("StringFormatMatches")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailUserFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.apply {
            tvName.text = args.dataUser.name?.let { convertNullToString(it) }
            tvUserName.text = args.dataUser.username?.let { convertNullToString(it) }
            tvBio.text = args.dataUser.bio?.let { convertNullToString(it) }
            tvCompany.text = args.dataUser.company?.let { convertNullToString(it) }
            tvLocation.text = args.dataUser.location?.let { convertNullToString(it) }
            tvRepoCount.text = args.dataUser.repository.toString()
            tvFollowersCount.text = args.dataUser.follower.toString()
            tvFollowingCount.text = args.dataUser.following.toString()
            titleActionBar.text = args.dataUser.name?.let{ convertNullToString(it)}

            val follower : String = convertIntValue(args.dataUser.follower)
            val following : String = convertIntValue(args.dataUser.following)
            val text = getString(R.string.followerfollowing, follower, following)
            Log.d(TAG,"$follower, $following, $text")


            tvFollowersFollowing.text = text
        }

        Glide.with(view)
            .load(args.dataUser.avatar)
            .apply(RequestOptions().override(55,55))
            .into(binding.ivUserPic)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = args.dataUser.username as String

        binding.toolbarId.setNavigationOnClickListener { activity?.onBackPressed(); }

        binding.apply {
            btnFollowers.setOnClickListener{
                val actionTo = DetailUserFragmentDirections.actionDetailUserFragmentToFollowerFollowingFragment(username)
                actionTo.position = 0
                findNavController().navigate(actionTo)
            }
            btnFollowing.setOnClickListener{
                val actionTo = DetailUserFragmentDirections.actionDetailUserFragmentToFollowerFollowingFragment(username)
                actionTo.position = 1
                findNavController().navigate(actionTo)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}