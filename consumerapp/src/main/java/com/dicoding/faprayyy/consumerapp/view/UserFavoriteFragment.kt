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
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.faprayyy.consumerapp.R
import com.dicoding.faprayyy.consumerapp.data.UserFavorite
import com.dicoding.faprayyy.consumerapp.databinding.FragmentUserFavoriteBinding
import com.dicoding.faprayyy.consumerapp.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserFavoriteFragment : Fragment() {

    companion object {
        const val AUTHORITY = "com.dicoding.faprayyy.githubuser"
        const val SCHEME = "content"
        const val TABLE_NAME = "favorite"
        const val DATA_USER = "data_user"

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

    }
    private var _binding: FragmentUserFavoriteBinding? = null
    private val binding get() = _binding as FragmentUserFavoriteBinding
    private lateinit var adapter: UserFavoriteAdapter
    private lateinit var mContext: Context
    private lateinit var mData: ArrayList<UserFavorite>
    val listUsers = MutableLiveData<ArrayList<UserFavorite>>()
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

        binding.rvUserFavorite.layoutManager = LinearLayoutManager(mContext)
        binding.rvUserFavorite.setHasFixedSize(true)
        adapter = UserFavoriteAdapter()
        binding.rvUserFavorite.adapter = adapter
        mData = ArrayList()
        listUsers.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

    inner class CallbackLoader : LoaderManager.LoaderCallbacks<Cursor>{
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
            binding.progressBar.visibility = View.VISIBLE
            return CursorLoader(mContext, CONTENT_URI, null, null, null, null)
        }

        override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
            binding.progressBar.visibility = View.INVISIBLE
            if (cursor != null){
                mData = MappingHelper.mapCursorToArrayList(cursor)
                listUsers.postValue(mData)
            } else {
                val string = resources.getString(R.string.no_data_favorite)
                Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show()
                listUsers.postValue(mData)
            }
        }

        override fun onLoaderReset(loader: Loader<Cursor>) {
        }
    }

    override fun onPause() {
        super.onPause()
        LoaderManager.getInstance(this).destroyLoader(0)
    }
    override fun onResume() {
        super.onResume()
        LoaderManager.getInstance(this).initLoader(0, null, CallbackLoader())
    }
}