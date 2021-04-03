package com.dicoding.faprayyy.githubuser.view.userdetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.faprayyy.githubuser.databinding.DetailUserFragmentBinding
import com.dicoding.faprayyy.githubuser.view.usersearch.UserSearchFragment
import java.math.BigDecimal
import java.math.RoundingMode

class DetailUserFragment : Fragment() {

    companion object {
        val TAG = UserSearchFragment::class.java.simpleName
    }

    private var _binding: DetailUserFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailUserViewModel

    private val args by navArgs<DetailUserFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailUserFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.tvName.text = args.dataUser.name?.let { checkNull(it) }
        binding.tvUserName.text = args.dataUser.username?.let { checkNull(it) }
        binding.tvBio.text = args.dataUser.bio?.let { checkNull(it) }
        binding.tvCompany.text = args.dataUser.company?.let { checkNull(it) }
        binding.tvLocation.text = args.dataUser.location?.let { checkNull(it) }
        binding.tvRepoCount.text = args.dataUser.repository.toString()
        binding.tvFollowersCount.text = args.dataUser.follower.toString()
        binding.tvFollowingCount.text = args.dataUser.following.toString()
        val convertText = "${convert_text(args.dataUser.follower)} followers ▪️ ${convert_text(args.dataUser.following)} following"
        binding.tvFollowersFollowing.text = convertText
        Glide.with(view)
            .load(args.dataUser.avatar)
            .apply(RequestOptions().override(55,55))
            .into(binding.ivUserPic)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

    }

    private fun checkNull(string: String): String{
        if (string == "null"){
            return "-"
        }
        return string
    }

    private fun convert_text(value: Int): String{
        val convertValue = value

        if(value >= 1000000){
            val cumacek = BigDecimal(35710000.toDouble()/1000000).setScale(2, RoundingMode.HALF_EVEN)
            Log.d(TAG, cumacek.toString())
            val decimalVal = convertValue.toDouble()/1000000
            val decimal = BigDecimal(decimalVal).setScale(2, RoundingMode.HALF_EVEN)
            return "${decimal}M"
        }
        else if(value>=1000)
        {
            val decimalVal = convertValue.toDouble()/1000
            val decimal = BigDecimal(decimalVal).setScale(2, RoundingMode.HALF_EVEN)
            return "${decimal}K"
        }
        return convertValue.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}