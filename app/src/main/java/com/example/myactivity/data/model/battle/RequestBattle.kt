package com.example.myactivity.data.model.battle


import com.google.gson.annotations.SerializedName

data class RequestBattle(
    @SerializedName("mode")
    val mode: String,
    @SerializedName("result")
    val result: String
)