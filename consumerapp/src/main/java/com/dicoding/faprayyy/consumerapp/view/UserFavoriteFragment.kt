package com.dicoding.faprayyy.consumerapp.view

import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.faprayyy.consumerapp.R
import com.dicoding.faprayyy.consumerapp.data.UserFavorite
import com.dicoding.faprayyy.consumerapp.databinding.FragmentUserFavoriteBinding
import com.dicoding.faprayyy.consumerapp.helper.MappingHelper
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
    private var _binding: FragmentUserFavoriteBinding? = null
    private val binding get() = _binding as FragmentUserFavoriteBinding
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
        _binding = FragmentUserFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserFavoriteAdapter()
        adapter.notifyDataSetChanged()
        binding.rvUserFavorite.layoutManager = LinearLayoutManager(activity)
        binding.rvUserFavorite.adapter = adapter
        val myObserver = object : ContentObserver(mHandler()) {
            override fun onChange(self: Boolean) {
                loadUsersAsync()
            }
        }
        activity?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadUsersAsync()
            activity?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)

        } else {
            val list = savedInstanceState.getParcelableArrayList<UserFavorite>(EXTRA_STATE)
            if (list != null) {
                adapter.mData = list
            }
        }
    }

    private fun mHandler() : Handler{
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        return Handler(handlerThread.looper)
    }

    private fun loadUsersAsync() {
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
                showSnackBarMessage(resources.getString(R.string.no_data_favorite))
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