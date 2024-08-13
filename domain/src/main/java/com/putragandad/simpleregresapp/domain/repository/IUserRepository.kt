package com.putragandad.simpleregresapp.domain.repository

import com.putragandad.simpleregresapp.domain.models.UserPagination

interface IUserRepository {
    suspend fun getUserRepository(page: Int) : UserPagination
}