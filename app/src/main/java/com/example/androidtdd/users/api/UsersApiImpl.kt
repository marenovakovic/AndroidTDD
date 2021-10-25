package com.example.androidtdd.users.api

import com.example.androidtdd.users.api.models.UserDto
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class UsersApiImpl @Inject constructor(
    private val httpClient: HttpClient,
) : UsersApi {
    override suspend fun fetchUsers(): List<UserDto> =
        httpClient.get("https://jsonplaceholder.typicode.com/users")

    override suspend fun fetchUser(userId: Int): UserDto =
        fetchUsers().first { it.id == userId }
}