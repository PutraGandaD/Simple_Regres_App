package com.putragandad.simpleregresapp.domain.models

data class UserPagination(
    val page: Int,
    val data: List<UserData>?
)
