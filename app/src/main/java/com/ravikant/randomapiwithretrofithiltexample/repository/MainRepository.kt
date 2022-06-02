package com.ravikant.randomapiwithretrofithiltexample.repository

import com.ravikant.randomapiwithretrofithiltexample.retrofit.ApiClients
import com.ravikant.randomapiwithretrofithiltexample.retrofit.AppInfoItem
import com.ravikant.randomapiwithretrofithiltexample.util.Resource
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: ApiClients
) {
    suspend fun getAppInfo(): Resource<AppInfoItem> {
        return try {
            val response = api.getAppInfo()
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An Error Occured")
        }
    }


    suspend fun getMultipleAppInfo(size: Int): Resource<List<AppInfoItem>> {
        return try {
            val response = api.getMultipleAppInfo(size)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An Error Occured")
        }
    }
}