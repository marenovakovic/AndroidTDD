package com.example.androidtdd.users.usecases

import com.example.androidtdd.usecases.UseCase
import com.example.androidtdd.users.api.UsersApi
import com.example.androidtdd.users.api.mappers.toUser
import com.example.androidtdd.users.api.models.UserDto
import com.example.androidtdd.users.models.User
import javax.inject.Inject

class FetchUsersUseCase @Inject constructor(
    private val usersApi: UsersApi,
) : UseCase<Unit, List<User>> {
    override suspend fun invoke(parameter: Unit): List<User> =
        usersApi.fetchUsers().map(UserDto::toUser)
}