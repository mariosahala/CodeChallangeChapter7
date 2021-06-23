package com.example.myactivity.data.model.register


import com.google.gson.annotations.SerializedName

data class RequestRegister(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)