package com.putragandad.simpleregresapp.data.source.remote

import com.putragandad.simpleregresapp.data.source.remote.response.ListUserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getListUser(page: Int) : ListUserResponse {
        return apiService.getListUser(page)
    }

}