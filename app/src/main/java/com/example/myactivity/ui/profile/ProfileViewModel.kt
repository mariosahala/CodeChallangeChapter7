package com.example.myactivity.ui.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myactivity.data.lokal.SharedPref
import com.example.myactivity.data.model.ResponseGet
import com.example.myactivity.data.remote.ApiClient
import retrofit2.Call
import retrofit2.Response

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    val pref = SharedPref(application)
    val token = pref.token.toString()
    val imageProfile = MutableLiveData<String>()


    fun getPhoto() {
        ApiClient.instance.getUser(token)
            .enqueue(object : retrofit2.Callback<ResponseGet> {
                override fun onResponse(call: Call<ResponseGet>, response: Response<ResponseGet>) {
                    imageProfile.value = response.body()?.data?.photo.toString()
                }

                override fun onFailure(call: Call<ResponseGet>, t: Throwable) {
                    Log.e("Error Update", t.message.toString())
                }
            })

    }
}