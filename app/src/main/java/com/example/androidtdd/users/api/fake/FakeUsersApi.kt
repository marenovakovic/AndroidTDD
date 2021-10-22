package com.example.androidtdd.users.api.fake

import com.example.androidtdd.users.api.UsersApi
import com.example.androidtdd.users.api.models.UserDto
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
object FakeUsersApi : UsersApi {
    override suspend fun fetchUsers(): List<UserDto> =
        Json.decodeFromString(fakeUserJson)
}