package com.example.myactivity.data.model.battle


import com.google.gson.annotations.SerializedName

data class ResponseBattle(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("success")
    val success: Boolean
) {
    data class Data(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("mode")
        val mode: String,
        @SerializedName("result")
        val result: String,
        @SerializedName("updatedAt")
        val updatedAt: String
    )
}