package com.putragandad.simpleregresapp.presentation.fragment.third

import com.putragandad.simpleregresapp.domain.models.UserData

data class ThirdScreenUiState(
    val totalPages: Int? = null,
    val totalItemPerPages: Int? = null,
    val userData: List<UserData>? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val message: String? = null
)