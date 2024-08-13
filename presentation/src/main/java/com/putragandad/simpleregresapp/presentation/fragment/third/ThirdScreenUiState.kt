package com.putragandad.simpleregresapp.presentation.fragment.third

import com.putragandad.simpleregresapp.domain.models.UserData

data class ThirdScreenUiState(
    val userData: List<UserData>? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val message: String? = null
)