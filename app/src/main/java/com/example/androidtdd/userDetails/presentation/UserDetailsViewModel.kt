package com.example.androidtdd.userDetails.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtdd.savedStateHandle.flow
import com.example.androidtdd.userDetails.presentation.UserDetailsState.UserDetailsState
import com.example.androidtdd.userDetails.usecases.FetchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    fetchUser: FetchUserUseCase,
    dispatcher: CoroutineDispatcher,
) : ViewModel() {
    val state: StateFlow<UserDetailsState> =
        savedStateHandle
            .flow(USER_ID, -1)
            .map { id -> fetchUser(id) }
            .map { user -> UserDetailsState.UserDetails(user) }
            .flowOn(dispatcher)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                UserDetailsState.Loading,
            )

    companion object {
        private const val USER_ID = "userId"
    }
}