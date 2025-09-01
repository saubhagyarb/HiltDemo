package com.saubh.hiltdemo.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponseItem(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("data")
    val data: Map<String, Any?>?
)
