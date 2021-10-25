package com.example.androidtdd.userDetails.presentation

import com.example.androidtdd.users.models.User

sealed class UserDetailsState {
    object Loading : UserDetailsState()
    data class UserDetails(val user: User) : UserDetailsState()
}