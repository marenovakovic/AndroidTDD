package com.example.androidtdd.users.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtdd.savedStateHandle.flow
import com.example.androidtdd.usecases.invoke
import com.example.androidtdd.users.usecases.FetchUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val fetchUsers: FetchUsersUseCase,
) : ViewModel() {
    private val query = savedStateHandle.flow(QUERY, "")

    val state: StateFlow<UsersState> =
        flow { emit(fetchUsers()) }
            .combine(query) { users, query ->
                val filteredUsers = users.filter { it.name.contains(query, ignoreCase = true) }
                filteredUsers to query
            }
            .map { (users, query) -> UsersState.Users(users, query) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UsersState.Loading,
            )

    fun search(query: String) {
        savedStateHandle[QUERY] = query
    }

    companion object {
        private const val QUERY = "query"
    }
}
