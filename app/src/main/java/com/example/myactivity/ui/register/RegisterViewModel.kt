package com.example.myactivity.ui.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myactivity.data.model.register.RequestRegister
import com.example.myactivity.data.model.ResponeError
import com.example.myactivity.data.model.register.ResponeRegister
import com.example.myactivity.data.remote.ApiClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    val resultOnSuccess = MutableLiveData<String>()
    val resultOnFailed = MutableLiveData<String>()
    val resultOnFailure = MutableLiveData<String>()

    fun onProcess(email: String, password: String, username: String) {
        val request = RequestRegister(email, password, username)

        ApiClient.instance.register(request)
            .enqueue(object : Callback<ResponeRegister> {
                override fun onResponse(
                    call: Call<ResponeRegister>,
                    response: Response<ResponeRegister>
                ) {
                    Log.e("Sukses Register", response.body().toString())
                    if (response.isSuccessful) {
                        resultOnSuccess.value = response.body().toString()
                    } else {
                        val responeError = Gson().fromJson(
                            response.errorBody()?.string(),
                            ResponeError::class.java
                        )
                        resultOnFailed.value = responeError.errors
                    }
                }

                override fun onFailure(call: Call<ResponeRegister>, t: Throwable) {
                    Log.e("Error Register", t.message.toString())
                    resultOnFailure.value = t.message.toString()
                }

            })
    }
}