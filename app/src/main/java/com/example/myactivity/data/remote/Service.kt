package com.example.myactivity.data.remote

import com.example.myactivity.data.model.ResponseGet
import com.example.myactivity.data.model.battle.GetBattleHistory
import com.example.myactivity.data.model.battle.RequestBattle
import com.example.myactivity.data.model.battle.ResponseBattle
import com.example.myactivity.data.model.login.RequestLogin
import com.example.myactivity.data.model.login.ResponeLogin
import com.example.myactivity.data.model.register.RequestRegister
import com.example.myactivity.data.model.register.ResponeRegister
import com.example.myactivity.data.model.update.ResponseUpdate
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Service {

    @POST ("/api/v1/auth/register")
    fun register (@Body body : RequestRegister) : Call<ResponeRegister>

    @POST("/api/v1/auth/login")
    fun login (@Body body: RequestLogin) : Call<ResponeLogin>

    @POST("/api/v1/battle")
    fun battle (@Header("Authorization") authorization: String, @Body body: RequestBattle) : Call<ResponseBattle>

    @GET("/api/v1/battle")
    fun getBattleHistory (@Header("Authorization") authorization: String) : Call<GetBattleHistory>

    @PUT("/api/v1/users")
    fun updateData(
        @Header("Authorization") authorization: String,
        @Body requestBody: RequestBody
    ): Call<ResponseUpdate>

    @GET("/api/v1/users")
    fun getUser (@Header ("Authorization") authorization: String) : Call<ResponseGet>

}