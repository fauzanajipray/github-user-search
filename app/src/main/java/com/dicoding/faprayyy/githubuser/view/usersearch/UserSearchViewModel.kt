package com.dicoding.faprayyy.githubuser.view.usersearch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.faprayyy.githubuser.datamodel.UserModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class UserSearchViewModel : ViewModel() {

    val apiKey = "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"
    val listUsers = MutableLiveData<ArrayList<UserModel>>()
    val listItems = ArrayList<UserModel>()

    fun setUser(query: String){
        listItems.clear()

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/search/users?q=$query"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    val jsonObjectResult = JSONObject(result)
                    val jsonArray = jsonObjectResult.getJSONArray("items")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val login: String = jsonObject.getString("login")
                        getDataGitDetail(login)
                    }

                } catch (e: Exception) {
                    Log.e("Exception", e.message.toString())
                    e.printStackTrace()
                }
            }
            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?) {
                Log.e("onFailure", error?.message.toString())
            }
        })
    }

    private fun getDataGitDetail(userName: String) {
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
                    val location: String = jsonObject.getString("location").toString()
                    val repository: Int = jsonObject.getInt("public_repos")
                    val followers: Int = jsonObject.getInt("followers")
                    val following: Int = jsonObject.getInt("following")
                    listItems.add(
                        UserModel(username,
                            name,
                            avatar,
                            company,
                            location,
                            repository,
                            followers,
                            following
                        )
                    )
                    listUsers.postValue(listItems)

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
                Log.e("onFailure", error?.message.toString())
            }
        })
    }

    fun getUsers(): LiveData<ArrayList<UserModel>> {
        Log.d("INI DATA LIVE", listUsers.toString())
        return listUsers
    }
}