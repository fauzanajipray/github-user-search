package com.dicoding.faprayyy.githubuser.view.userdetail

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.faprayyy.githubuser.R
import com.dicoding.faprayyy.githubuser.data.UserFavorite
import com.dicoding.faprayyy.githubuser.data.UserFavoriteDatabase
import com.dicoding.faprayyy.githubuser.data.UserFavoriteRepository
import com.dicoding.faprayyy.githubuser.databinding.DetailUserFragmentBinding
import com.dicoding.faprayyy.githubuser.datamodel.UserModel
import com.dicoding.faprayyy.githubuser.utils.convertNullToString
import com.dicoding.faprayyy.githubuser.view.favorituser.UserFavoriteViewModel
import com.google.android.material.snackbar.Snackbar

class DetailUserFragment : Fragment() {

    companion object {
        val TAG = this::class.java.simpleName
    }

    private var _binding: DetailUserFragmentBinding? = null
    private val binding get() = _binding as DetailUserFragmentBinding
    private lateinit var user : UserModel
    private val args by navArgs<DetailUserFragmentArgs>()
    private lateinit var database: UserFavoriteDatabase
    private lateinit var imgNotFavorite : Drawable
    private lateinit var imgFavorite : Drawable
    private var stateFavorite : Boolean = true
    private lateinit var mUserFavRepo : UserFavoriteRepository
    private lateinit var mUserFavoriteViewModel: UserFavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = UserFavoriteDatabase.getDatabase(context as Context)
        mUserFavRepo = UserFavoriteRepository(database.userFavDao())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailUserFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        imgNotFavorite =  ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_favorite_border_24, null) as Drawable
        imgFavorite =  ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_favorite_24, null) as Drawable
        mUserFavoriteViewModel = ViewModelProvider(this).get(UserFavoriteViewModel::class.java)

        stateFavorite = false
        user = args.dataUser
        getUserFavFromDB(user.username as String)
        user.let {
            binding.apply {
                tvName.text = it.name?.let { convertNullToString(it) }
                tvUserName.text = it.username?.let { convertNullToString(it) }
                tvBio.text = it.bio?.let { convertNullToString(it) }
                tvCompany.text = it.company?.let { convertNullToString(it) }
                tvLocation.text = it.location?.let { convertNullToString(it) }
                tvRepoCount.text = it.repository.toString()
                tvFollowersCount.text = it.follower.toString()
                tvFollowingCount.text = it.following.toString()
                titleActionBar.text = it.name?.let{ convertNullToString(it)}
                val follower : String = convertIntValue(it.follower)
                val following : String = convertIntValue(it.following)
                val text = getString(R.string.followerfollowing, follower, following)
                tvFollowersFollowing.text = text
            }
            Glide.with(view)
                .load(it.avatar)
                .apply(RequestOptions().override(55,55))
                .into(binding.ivUserPic)
        }
        changeImgFAB(stateFavorite)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = args.dataUser.username as String

        binding.toolbarId.setNavigationOnClickListener { activity?.onBackPressed() }

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
        val fab = binding.fabAdd
        val userFavorite = user.let {
            UserFavorite(it.username.toString(), it.name, it.avatar, it.bio, it.company,
                    it.location, it.repository, it.follower, it.following)
        }

        fab.setOnClickListener{
            if(stateFavorite){
                deleteUserFavFromDB(userFavorite)
            } else {
                addUserFavtoDB(userFavorite)
            }
        }
    }

    private fun getUserFavFromDB(userName: String) {

        mUserFavoriteViewModel.readUserById(userName)
        mUserFavoriteViewModel.getUserById().observe(viewLifecycleOwner) {
            stateFavorite = false
            if (it != null) {
                stateFavorite = true
                changeImgFAB(stateFavorite)
            } else {
                stateFavorite = false
                changeImgFAB(stateFavorite)
            }
        }
    }

    private fun addUserFavtoDB(user : UserFavorite) {
        mUserFavoriteViewModel.addUser(user)
        stateFavorite = true
        changeImgFAB(stateFavorite)
        val string = getString(R.string.add_favorite, user.username)
        showSnackBarMessage(string)
    }

    private fun changeImgFAB(state : Boolean) {
        if (state) {
            binding.fabAdd.setImageDrawable(imgFavorite)
        } else {
            binding.fabAdd.setImageDrawable(imgNotFavorite)
        }
    }

    private fun deleteUserFavFromDB(user: UserFavorite) {
        mUserFavoriteViewModel.deleteUser(user)
        val string = getString(R.string.remove_favorite, user.username)
        showSnackBarMessage(string)
        changeImgFAB(stateFavorite)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.mainLayout, message, Snackbar.LENGTH_SHORT).show()
    }

}