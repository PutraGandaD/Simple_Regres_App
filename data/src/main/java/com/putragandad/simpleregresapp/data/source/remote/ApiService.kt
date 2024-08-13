package com.putragandad.simpleregresapp.data.source.remote

import com.putragandad.simpleregresapp.data.source.remote.response.ListUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getListUser(
        @Query("page") page: Int
    ): ListUserResponse
}