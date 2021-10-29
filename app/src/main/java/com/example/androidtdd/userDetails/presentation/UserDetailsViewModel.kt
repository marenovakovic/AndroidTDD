package com.example.androidtdd.userDetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtdd.savedStateHandle.flow
import com.example.androidtdd.userDetails.usecases.FetchUserUseCase
import com.example.androidtdd.users.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    fetchUser: FetchUserUseCase,
) : ViewModel() {
    val state: StateFlow<UserDetailsState> =
        savedStateHandle
            .flow(USER_ID, -1)
            .map { id -> fetchUser(id) }
            .map<User, UserDetailsState> { user -> UserDetailsState.UserDetails(user) }
            .onStart { emit(UserDetailsState.Loading) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                UserDetailsState.Loading,
            )

    companion object {
        private const val USER_ID = "userId"
    }
}