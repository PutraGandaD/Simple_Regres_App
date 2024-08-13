package com.putragandad.simpleregresapp.data.implementation

import com.putragandad.simpleregresapp.data.mapper.ResponseMapper
import com.putragandad.simpleregresapp.data.source.remote.RemoteDataSource
import com.putragandad.simpleregresapp.domain.models.UserPagination
import com.putragandad.simpleregresapp.domain.repository.IUserRepository

class UserRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : IUserRepository {
    override suspend fun getUserRepository(page: Int): UserPagination {
        val response = remoteDataSource.getListUser(page)
        return ResponseMapper.userResponseToDomain(response)
    }
}