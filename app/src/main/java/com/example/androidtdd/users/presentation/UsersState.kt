package com.example.androidtdd.users.presentation

import com.example.androidtdd.users.models.User

sealed class UsersState {
    object Loading : UsersState()
    data class Users(
        val users: List<User>,
        val query: String,
    ) : UsersState()
}
