package com.example.myactivity.ui.editprofile

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myactivity.data.lokal.SharedPref
import com.example.myactivity.data.model.ResponseGet
import com.example.myactivity.data.model.update.ResponseUpdate
import com.example.myactivity.data.remote.ApiClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File

class EditProfileViewModel(application: Application) : AndroidViewModel(application) {
    var path = MutableLiveData<String>()
    val resultSuksesUpdateData = MutableLiveData<String>()
    val resultErrorUpdateData = MutableLiveData<String>()
    val pref = SharedPref(application)
    val token = pref.token.toString()
    val imageProfile = MutableLiveData<String>()


    fun update(username: String, email: String) {

        val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("username", username)
            .addFormDataPart("email", email)
            .addFormDataPart(
                "photo", "514607f82268910b76093d1ba35cc6c0.jpg",
                File(path.value.toString()).asRequestBody("application/octet-stream".toMediaTypeOrNull())
            ).build()

        ApiClient.instance.updateData(token, body)
            .enqueue(object : retrofit2.Callback<ResponseUpdate> {
                override fun onFailure(call: retrofit2.Call<ResponseUpdate>, t: Throwable) {
                    Log.e("Error Update", t.message.toString())
                    resultErrorUpdateData.value = t.message.toString()
                }

                override fun onResponse(
                    call: retrofit2.Call<ResponseUpdate>,
                    response: retrofit2.Response<ResponseUpdate>
                ) {
                    Log.e("Sukses Update", response.body().toString())
                    resultSuksesUpdateData.value = response.body().toString()
                    pref.username = response.body()?.data?.username
                    pref.email = response.body()?.data?.email
                }

            })

    }

    fun image() {
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