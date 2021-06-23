package com.example.myactivity.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myactivity.data.lokal.SharedPref
import com.example.myactivity.data.model.ResponeError
import com.example.myactivity.data.model.login.RequestLogin
import com.example.myactivity.data.model.login.ResponeLogin
import com.example.myactivity.data.remote.ApiClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val resultOnSuccess = MutableLiveData<String>()
    val resultOnFailed = MutableLiveData<String>()
    val resultOnFailure = MutableLiveData<String>()
    val sharedPref = SharedPref(application)

    fun setContent(email: String, password: String) {
        val request = RequestLogin(email, password)

        ApiClient.instance.login(request)
            .enqueue(object : Callback<ResponeLogin> {
                override fun onResponse(
                    call: Call<ResponeLogin>,
                    response: Response<ResponeLogin>
                ) {
                    Log.e("Sukses Login", response.body().toString())
                    if (response.isSuccessful) {
                        resultOnSuccess.value = response.body()?.data.toString()
                        sharedPref.username = response.body()?.data?.username
                        sharedPref.email = response.body()?.data?.email
                        sharedPref.token = "Bearer ${response.body()?.data?.token.toString()}"
                        sharedPref.isLogin = true
                    } else {
                        val responseError = Gson().fromJson(
                            response.errorBody()?.string(),
                            ResponeError::class.java
                        )
                        resultOnFailed.value = responseError.errors
                    }
                }

                override fun onFailure(call: Call<ResponeLogin>, t: Throwable) {
                    Log.e("Error Register", t.message.toString())
                    resultOnFailure.value = t.message.toString()
                }

            })
    }
}