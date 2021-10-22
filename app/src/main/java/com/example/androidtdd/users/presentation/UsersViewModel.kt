package com.example.androidtdd.users.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidtdd.savedStateHandle.SavedStateHandleListDelegate
import com.example.androidtdd.usecases.invoke
import com.example.androidtdd.users.models.User
import com.example.androidtdd.users.usecases.FetchUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    dispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
    private val fetchUsers: FetchUsersUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<UsersState> = MutableStateFlow(UsersState.Loading)
    val state: StateFlow<UsersState> = _state.asStateFlow()

    private var users: List<User> by SavedStateHandleListDelegate(USERS, savedStateHandle)

    init {
        viewModelScope.launch(dispatcher) {
            users = fetchUsers()
            _state.value = UsersState.Users(users)
        }
    }

    fun search(query: String) {
        val queriedUsers = users.filter { it.name.contains(query, ignoreCase = true) }
        _state.value = UsersState.Users(queriedUsers)
    }

    companion object {
        private const val USERS = "users"
    }
}
