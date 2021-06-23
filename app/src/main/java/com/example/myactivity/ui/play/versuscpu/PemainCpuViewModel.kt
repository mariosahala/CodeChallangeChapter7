package com.example.myactivity.ui.play.versuscpu

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myactivity.data.lokal.SharedPref
import com.example.myactivity.data.model.battle.RequestBattle
import com.example.myactivity.data.model.battle.ResponseBattle
import com.example.myactivity.data.remote.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PemainCpuViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "Player Vs Computer"
    private var listRandom = mutableListOf("gunting", "batu", "kertas")
    private var loopRandom = 0
    private val sharedPref = SharedPref(application)
    val username = sharedPref.username
    val resultBattle = MutableLiveData<String>()
    val resultRandom = MutableLiveData<String>()
    val resultRandomFinish = MutableLiveData<String>()

    fun activateRandom() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (loopRandom <= 15) {
                resultRandom.value = listRandom.random()
                loopRandom++
                Log.e(TAG, "Perulangan Computer #${loopRandom}")
                activateRandom()
            } else {
                resultRandomFinish.value = listRandom.random()
                loopRandom = 0
            }
        }, 1000L)
    }

    fun battle(player: String, cpu: String) {
        resultBattle.value =
            if (player == "batu" && cpu == "gunting" || player == "kertas" && cpu == "batu" || player == "gunting" && cpu == "kertas") {
                "Player Win"
            } else if (cpu == "batu" && player == "gunting" || cpu == "kertas" && player == "batu" || cpu == "gunting" && player == "kertas") {
                "Computer Win"
            } else {
                "Draw"
            }
        saveBattle()
    }

    private fun saveBattle() {
        val hasil = resultBattle.value.toString()
        val requestBattle = RequestBattle("Singleplayer", hasil)
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