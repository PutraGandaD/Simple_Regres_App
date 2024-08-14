package com.putragandad.simpleregresapp.data.mapper

import com.putragandad.simpleregresapp.data.source.remote.response.ListUserResponse
import com.putragandad.simpleregresapp.domain.models.UserData
import com.putragandad.simpleregresapp.domain.models.UserPagination

object ResponseMapper {
    fun userResponseToDomain(response: ListUserResponse) : UserPagination {
        return UserPagination(
            currentPage = response.page,
            totalPages = response.totalPages,
            totalItemPerPages = response.perPage,
            data = response.data?.map {
                UserData(
                    avatar = it.avatar,
                    email = it.email,
                    firstName = it.firstName,
                    lastName = it.lastName
                )
            }
        )
    }
}