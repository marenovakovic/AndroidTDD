package com.example.androidtdd.userDetails.usecases

import com.example.androidtdd.usecases.UseCase
import com.example.androidtdd.users.api.UsersApi
import com.example.androidtdd.users.api.mappers.toUser
import com.example.androidtdd.users.models.User
import javax.inject.Inject

class FetchUserUseCase @Inject constructor(
    private val usersApi: UsersApi,
) : UseCase<Int, User> {
    override suspend fun invoke(userId: Int): User =
        usersApi.fetchUser(userId).toUser()
}