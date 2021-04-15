package com.dicoding.faprayyy.githubuser.view.favorituser

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.faprayyy.githubuser.R
import com.dicoding.faprayyy.githubuser.adapter.UserFavoriteAdapter
import com.dicoding.faprayyy.githubuser.data.UserFavorite
import com.dicoding.faprayyy.githubuser.databinding.FragmentFavoriteUserBinding
import com.dicoding.faprayyy.githubuser.datamodel.UserModel
import com.dicoding.faprayyy.githubuser.helper.MappingHelper
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserFavoriteFragment : Fragment() {

    companion object {
        private const val AUTHORITY = "com.dicoding.faprayyy.githubuser"
        private const val SCHEME = "content"
        private const val TABLE_NAME = "favorite"
        private const val EXTRA_STATE = "EXTRA_STATE"

        private val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()

    }
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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = UserFavoriteAdapter()
        adapter.notifyDataSetChanged()
        binding.rvUserFavorite.layoutManager = LinearLayoutManager(activity)
        binding.rvUserFavorite.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadNotesAsync()
            }
        }
        activity?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<UserFavorite>(EXTRA_STATE)
            if (list != null) {
                adapter.mData = list
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

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility = View.VISIBLE

            val deferredNotes = async(Dispatchers.IO) {
                val cursor = activity?.contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            binding.progressBar.visibility = View.INVISIBLE
            val users = deferredNotes.await()
            if (users.size > 0) {
                adapter.setData(users)
            } else {
                adapter.setData(ArrayList())
                showSnackBarMessage(resources.getString(R.string.you_don_t_have_any_favorite_users))
            }
        }
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.rvUserFavorite, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.mData)
    }

}