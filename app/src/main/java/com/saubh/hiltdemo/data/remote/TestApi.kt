package com.saubh.hiltdemo.data.remote

import com.saubh.hiltdemo.data.model.ApiResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface TestApi {
    @GET("objects")
    suspend fun getObjects(): Response<List<ApiResponseItem>>
}
