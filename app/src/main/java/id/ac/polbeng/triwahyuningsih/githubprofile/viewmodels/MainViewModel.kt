
package id.ac.polbeng.triwahyuningsih.githubprofile.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.polbeng.triwahyuningsih.githubprofile.helpers.Config
import id.ac.polbeng.triwahyuningsih.githubprofile.models.GithubUser
import id.ac.polbeng.triwahyuningsih.githubprofile.services.GithubUserService
import id.ac.polbeng.triwahyuningsih.githubprofile.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        val TAG: String = MainViewModel::class.java.simpleName
    }

    private val _githubUser = MutableLiveData<GithubUser?>()
    val githubUser: LiveData<GithubUser?> = _githubUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        searchUser(Config.DEFAULT_USER_LOGIN)
    }

    fun searchUser(query: String) {
        _isLoading.value = true
        Log.d(TAG, "getDataUserProfileFromAPI: start...")

        val githubUserService =
            ServiceBuilder.buildService(GithubUserService::class.java)

        val requestCall: Call<GithubUser> = githubUserService.loginUser(query)

        Log.d(TAG, "Request URL: ${requestCall.request().url}")

        requestCall.enqueue(object : Callback<GithubUser> {

            override fun onResponse(
                call: Call<GithubUser>,
                response: Response<GithubUser>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d(TAG, result.toString())
                    _githubUser.postValue(result)
                    Log.d(TAG, "getDataUserFromAPI: onResponse finish...")
                } else {
                    _githubUser.postValue(null)
                    Log.e(TAG, "Request gagal: HTTP ${response.code()}")
                }
            }

            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                _isLoading.value = false
                _githubUser.postValue(null)
                Log.e(TAG, "getDataUserFromAPI: onFailure ${t.message}", t)
            }
        })
    }
}
