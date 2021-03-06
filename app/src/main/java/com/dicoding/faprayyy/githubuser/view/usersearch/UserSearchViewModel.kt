package com.dicoding.faprayyy.githubuser.view.usersearch

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.faprayyy.githubuser.datamodel.UserModel
import com.dicoding.faprayyy.githubuser.utils.utils
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class UserSearchViewModel(application: Application) : AndroidViewModel(application) {

    private val apiKey = utils.apiKey
    // TODO GITHUB KEY
    // private val apiKey = BuildConfig.GITHUB_TOKEN

    val listUsers = MutableLiveData<ArrayList<UserModel>>()
    val listItems = ArrayList<UserModel>()
    val searchStateLive = MutableLiveData<Boolean>()
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    companion object{
        var notFoundText = "not_found"
    }

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
                    val totalCount = jsonObjectResult.getInt("total_count")
                    if (totalCount != 0){
                        val jsonArray = jsonObjectResult.getJSONArray("items")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val login: String = jsonObject.getString("login")
                            getDataGitDetail(login)
                        }
                        searchStateLive.postValue(true)
                    } else {
                        searchStateLive.postValue(false)
                        Toast.makeText(context, notFoundText , Toast.LENGTH_SHORT).show()
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
                Log.e("onFailure 1", error?.message.toString())
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
                    val bio: String = jsonObject.getString("bio").toString()
                    val location: String = jsonObject.getString("location").toString()
                    val repository: Int = jsonObject.getInt("public_repos")
                    val followers: Int = jsonObject.getInt("followers")
                    val following: Int = jsonObject.getInt("following")
                    listItems.add(
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
                Log.e("onFailure 2", error?.message.toString())
            }
        })
    }

    fun getUsers(): LiveData<ArrayList<UserModel>> {
        UserSearchFragment.stateTvSearchMsg = listItems.toString() == "[]"
        return listUsers
    }
    fun getStateSearch(): LiveData<Boolean> {
        return searchStateLive
    }

}