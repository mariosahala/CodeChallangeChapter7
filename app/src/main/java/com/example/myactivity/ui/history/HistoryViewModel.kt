package com.example.myactivity.ui.history

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myactivity.data.lokal.SharedPref
import com.example.myactivity.data.model.battle.GetBattleHistory
import com.example.myactivity.data.remote.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel(application: Application): AndroidViewModel(application) {
    val tag = "Skor"
    val sharedPref = SharedPref(application)
    val token = sharedPref.token.toString()
    var result = MutableLiveData<List<GetBattleHistory.Data>>()
    fun listScore() {

        ApiClient.instance.getBattleHistory(token)
            .enqueue(object : Callback<GetBattleHistory> {
                override fun onFailure(call: Call<GetBattleHistory>, t: Throwable) {
                    Log.e("GetBattleHistory Failed", "onFailure: ${t.message.toString()}")
                }

                override fun onResponse(
                    call: Call<GetBattleHistory>,
                    response: Response<GetBattleHistory>
                ) {
                    result.value = response.body()?.data
                }
            })
    }
}