package com.example.myactivity.data.model

import com.google.gson.annotations.SerializedName

data class ResponeError(
    @SerializedName("errors")
    val errors: String,
    @SerializedName("success")
    val success: Boolean
)