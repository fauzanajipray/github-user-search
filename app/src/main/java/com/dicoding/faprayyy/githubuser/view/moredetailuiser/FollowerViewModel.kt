package com.dicoding.faprayyy.githubuser.view.moredetailuiser

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.faprayyy.githubuser.BuildConfig
import com.dicoding.faprayyy.githubuser.datamodel.UserModel
import com.dicoding.faprayyy.githubuser.utils.utils
import com.dicoding.faprayyy.githubuser.view.usersearch.UserSearchViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class FollowerViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext


    private val apiKey = utils.apiKey
    // TODO GITHUB KEY
    // private val apiKey = BuildConfig.GITHUB_TOKEN

    val listFollower = MutableLiveData<ArrayList<UserModel>>()
    private val listItemsFollower = ArrayList<UserModel>()
    val searchStateLive = MutableLiveData<Boolean>()

    fun setFollower(userName: String){
        listItemsFollower.clear()

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")

        val url = "https://api.github.com/users/$userName/followers"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                if (result == "[]"){
                    Toast.makeText(context, UserSearchViewModel.notFoundText, Toast.LENGTH_SHORT).show()
                    searchStateLive.postValue(false)
                } else {
                    try {
                        val jsonArray = JSONArray(result)
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val login: String = jsonObject.getString("login")
                            getDataUserDetail(login)
                        }
                        searchStateLive.postValue(true)
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
                error: Throwable?
            ) {
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
                    listItemsFollower.add(
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
                    listFollower.postValue(listItemsFollower)

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
    fun getFollower(): LiveData<ArrayList<UserModel>> {
        return listFollower
    }
    fun getStateSearch(): LiveData<Boolean> {
        return searchStateLive
    }
}