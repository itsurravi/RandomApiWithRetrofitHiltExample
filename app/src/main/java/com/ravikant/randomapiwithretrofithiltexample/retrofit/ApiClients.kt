package com.ravikant.randomapiwithretrofithiltexample.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClients {

    @GET("app/random_app")
    suspend fun getAppInfo() : Response<AppInfoItem>

    @GET("app/random_app")
    suspend fun getMultipleAppInfo(@Query("size") size: Int) : Response<List<AppInfoItem>>
}