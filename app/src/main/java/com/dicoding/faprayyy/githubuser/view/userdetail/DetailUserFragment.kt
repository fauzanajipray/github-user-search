package com.dicoding.faprayyy.githubuser.view.userdetail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.faprayyy.githubuser.databinding.DetailUserFragmentBinding
import com.dicoding.faprayyy.githubuser.utils.ConvertNullToString
import com.dicoding.faprayyy.githubuser.view.usersearch.UserSearchFragment

class DetailUserFragment : Fragment() {

    companion object {
        val TAG = UserSearchFragment::class.java.simpleName
    }

    private var _binding: DetailUserFragmentBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailUserFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailUserFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.apply {
            tvName.text = args.dataUser.name?.let { ConvertNullToString(it) }
            tvUserName.text = args.dataUser.username?.let { ConvertNullToString(it) }
            tvBio.text = args.dataUser.bio?.let { ConvertNullToString(it) }
            tvCompany.text = args.dataUser.company?.let { ConvertNullToString(it) }
            tvLocation.text = args.dataUser.location?.let { ConvertNullToString(it) }
            tvRepoCount.text = args.dataUser.repository.toString()
            tvFollowersCount.text = args.dataUser.follower.toString()
            tvFollowingCount.text = args.dataUser.following.toString()
            val convertText = "${convertintvalue(args.dataUser.follower)} followers ▪️ ${convertintvalue(args.dataUser.following)} following"
            tvFollowersFollowing.text = convertText
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

        binding.toolbarId.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                getActivity()?.onBackPressed();
            }
        })

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