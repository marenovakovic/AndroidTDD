package com.example.androidtdd.users.api

import com.example.androidtdd.users.api.models.UserDto

interface UsersApi {
    suspend fun fetchUsers(): List<UserDto>
    suspend fun fetchUser(userId: Int): UserDto
}