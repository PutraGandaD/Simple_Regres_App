package com.putragandad.simpleregresapp.domain.usecase

import com.putragandad.simpleregresapp.common.Resource
import com.putragandad.simpleregresapp.domain.models.UserPagination
import com.putragandad.simpleregresapp.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetListUserUseCase(
    private val userRepository: IUserRepository
) {
    suspend operator fun invoke(page: Int) : Flow<Resource<UserPagination>> = flow {
        emit(Resource.Loading())
        try {
            val response = userRepository.getUserRepository(page)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Server error!"))
        }
    }
}