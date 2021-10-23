package com.example.androidtdd.users.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtdd.savedStateHandle.flow
import com.example.androidtdd.usecases.invoke
import com.example.androidtdd.users.models.User
import com.example.androidtdd.users.usecases.FetchUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    dispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle,
    private val fetchUsers: FetchUsersUseCase,
) : ViewModel() {
    private val users = savedStateHandle.flow(USERS, emptyList<User>())
    private val query = savedStateHandle.flow(QUERY, "")

    val state: StateFlow<UsersState> =
        users
            .map { savedUsers ->
                if (savedUsers.isEmpty()) fetchUsers().also {
                    savedStateHandle[USERS] = it
                } else savedUsers
            }
            .combine(query) { users, query ->
                users.filter { it.name.contains(query, ignoreCase = true) }
            }
            .map { UsersState.Users(it) }
            .flowOn(dispatcher)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = UsersState.Loading,
            )

    fun search(query: String) {
        savedStateHandle[QUERY] = query
    }

    companion object {
        private const val USERS = "users"
        private const val QUERY = "query"
    }
}
