package com.example.myactivity.ui.play.versusplayer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myactivity.data.lokal.SharedPref
import com.example.myactivity.data.model.battle.RequestBattle
import com.example.myactivity.data.model.battle.ResponseBattle
import com.example.myactivity.data.remote.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PemainPemainViewModel(application: Application) : AndroidViewModel(application) {
    private val result = MutableLiveData<String>()
    private var sharedPref = SharedPref(application)
    var rival: String = "rival"
    var pemain: String = sharedPref.username.toString()
    var pilihan = ""
    var pilihanLawan = ""
    fun result(): LiveData<String> = result
    var skor = 0


    fun play() {
        if (pilihan == pilihanLawan) {
            result.value = "Draw"
        } else {
            if (pilihan == "gunting" && pilihanLawan == "kertas" || pilihan == "kertas" && pilihanLawan == "batu" || pilihan == "batu" && pilihanLawan == "gunting") {
                skor = 1
                result.value = "$pemain \n WIN!"
            } else {
                skor = 2
                result.value = "$rival \n WIN!"
            }
        }
    }

    fun simpanBattle() {
        val hasil: String = when (skor) {
            1 -> {
                "Player Win"
            }
            2 -> {
                "Opponent Win"
            }
            else -> {
                "Draw"
            }
        }

        val requestBattle = RequestBattle("Multiplayer", hasil)
        ApiClient.instance.battle(sharedPref.token.toString(), requestBattle)
            .enqueue(object : Callback<ResponseBattle> {
                override fun onFailure(call: Call<ResponseBattle>, t: Throwable) {
                    Log.e("Request Battle", "onFailure: ${t.message}")
                }

                override fun onResponse(
                    call: Call<ResponseBattle>,
                    response: Response<ResponseBattle>
                ) {
                    Log.d("Response Battle", "onSuccess: ${response.body().toString()}")
                }
            })
    }
}