package com.dicoding.faprayyy.githubuser.view.moredetailuiser

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.faprayyy.githubuser.datamodel.UserModel
import com.dicoding.faprayyy.githubuser.utils.utils
import com.dicoding.faprayyy.githubuser.view.usersearch.UserSearchViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class FollowingViewModel(application: Application) : AndroidViewModel(application) {

    companion object{
        val TAG = this::class.java.simpleName
    }

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    val apiKey = utils.apiKey

    val listFollowing = MutableLiveData<ArrayList<UserModel>>()
    val listItemsFollowing = ArrayList<UserModel>()
    val searchStateLive = MutableLiveData<Boolean>()

    fun setFollowing(userName: String){
        listItemsFollowing.clear()
        Log.d(TAG, "DATA FOLLOWING1 : $userName , $listItemsFollowing")

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")

        val url = "https://api.github.com/users/$userName/following"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray) {
                val result = String(responseBody)
                if (result == "[]"){
                    Toast.makeText(context, UserSearchViewModel.notFoundText, Toast.LENGTH_SHORT).show()
                    searchStateLive.postValue(false)
                } else {
                    try {
                        val jsonArray = JSONArray(result)
                        Log.d(TAG, "DATA FOLLOWING2 : $jsonArray")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val login: String = jsonObject.getString("login")
                            getDataUserDetail(login)
                            searchStateLive.postValue(true)
                        }
                    } catch (e: Exception) {
                        Log.e("Exception", e.message.toString())
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?) {
                Log.e("onFailure 1", error?.message.toString())
            }
        })
    }

    private fun getDataUserDetail(userName: String) {
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$userName"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val jsonObject = JSONObject(result)
                    val username: String = jsonObject.getString("login").toString()
                    val name: String = jsonObject.getString("name").toString()
                    val avatar: String = jsonObject.getString("avatar_url").toString()
                    val company: String = jsonObject.getString("company").toString()
                    val bio: String = jsonObject.getString("bio").toString()
                    val location: String = jsonObject.getString("location").toString()
                    val repository: Int = jsonObject.getInt("public_repos")
                    val followers: Int = jsonObject.getInt("followers")
                    val following: Int = jsonObject.getInt("following")
                    listItemsFollowing.add(
                        UserModel(
                            username,
                            name,
                            avatar,
                            bio,
                            company,
                            location,
                            repository,
                            followers,
                            following
                        )
                    )
                    listFollowing.postValue(listItemsFollowing)
                } catch (e: Exception) {
                    Log.e("Exception", e.message.toString())
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                Log.e("onFailure 2", error?.message.toString())
            }
        })
    }

    fun getFollowing(): LiveData<ArrayList<UserModel>> {
        return listFollowing
    }
    fun getStateSearch(): LiveData<Boolean> {
        return searchStateLive
    }
}