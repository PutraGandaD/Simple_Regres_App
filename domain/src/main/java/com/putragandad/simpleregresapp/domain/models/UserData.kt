package com.putragandad.simpleregresapp.domain.models

import com.google.gson.annotations.SerializedName

data class UserData(
    val avatar: String,
    val email: String,
    val firstName: String,
    val lastName: String
)
