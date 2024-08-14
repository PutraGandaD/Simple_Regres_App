package com.putragandad.simpleregresapp.domain.models

data class UserPagination(
    val currentPage: Int,
    val totalPages: Int,
    val totalItemPerPages: Int,
    val data: List<UserData>?
)
