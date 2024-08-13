package com.putragandad.simpleregresapp.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class ListUserResponse(
    @SerializedName("data")
    val data: List<ListUserData>?,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("support")
    val support: Support,
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)