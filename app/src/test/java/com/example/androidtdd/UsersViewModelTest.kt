package com.example.androidtdd

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import com.example.androidtdd.users.api.fake.FakeUsersApi
import com.example.androidtdd.users.presentation.UsersState
import com.example.androidtdd.users.presentation.UsersViewModel
import com.example.androidtdd.users.usecases.FetchUsersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest {
    private val fetchUsersUseCase = FetchUsersUseCase(FakeUsersApi)
    private lateinit var viewModel: UsersViewModel

    private val usersState: UsersState.Users
        get() = viewModel.state.value as UsersState.Users

    @BeforeTest
    fun setUp() {
        val dispatcher = TestCoroutineDispatcher()
        viewModel = UsersViewModel(dispatcher, SavedStateHandle(), fetchUsersUseCase)
    }

    @SuppressLint("IgnoreWithoutReason")
    @Ignore
    @Test
    fun `should emit Loading when created`() = runBlocking {
        val states = mutableListOf<UsersState>()
        val job = launch {
            viewModel.state.toList(states)
        }

        assertSame(states.first(), UsersState.Loading)
        assertTrue(states[1] is UsersState.Users)
    }

    @Test
    fun `should fetch users when created`() {
        assertTrue(viewModel.state.value is UsersState.Users)
    }

    @Test
    fun `search should filter users by name`() {
        val query = "Ervin"
        viewModel.search(query)

        assertUsersStateContainsOnlySearchedUsers(usersState, query)
    }

    @Test
    fun `previous searches should not affect future searches`() {
        val firstQuery = "Ervin"
        viewModel.search(firstQuery)

        assertTrue(usersState.users.isNotEmpty())
        assertUsersStateContainsOnlySearchedUsers(usersState, firstQuery)

        val secondQuery = "Leanne"
        viewModel.search(secondQuery)

        assertTrue(usersState.users.isNotEmpty())
        assertUsersStateContainsOnlySearchedUsers(usersState, secondQuery)
    }

    private fun assertUsersStateContainsOnlySearchedUsers(
        state: UsersState.Users,
        query: String,
    ) {
        assertTrue(state.users.all { it.name.contains(query, ignoreCase = true) })
        assertTrue(state.users.none { !it.name.contains(query, ignoreCase = true) })
    }

    @Test
    fun `empty query should show all users`() {
        val allUsers = usersState.users

        val query = "Ervin"
        viewModel.search(query)
        viewModel.search("")

        assertEquals(usersState.users, allUsers)
    }
}