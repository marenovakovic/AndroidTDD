package com.example.androidtdd

import android.annotation.SuppressLint
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.androidtdd.usecases.invoke
import com.example.androidtdd.users.api.fake.FakeUsersApi
import com.example.androidtdd.users.presentation.UsersState
import com.example.androidtdd.users.presentation.UsersViewModel
import com.example.androidtdd.users.usecases.FetchUsersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.Ignore
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val fetchUsersUseCase = FetchUsersUseCase(FakeUsersApi)
    private lateinit var viewModel: UsersViewModel

    private val usersState: UsersState.Users
        get() = viewModel.state.value as UsersState.Users

    @Before
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

        job.cancel()
    }

    @SuppressLint("IgnoreWithoutReason")
    @Ignore
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

    @Test
    fun `empty query should show all users`() = runBlocking {
        val allUsers = fetchUsersUseCase()

        val query = "Ervin"
        viewModel.search(query)
        viewModel.search("")

        assertEquals(usersState.users, allUsers)
    }

    @Test
    fun `should show same query after process death`() = runBlocking {
        val dispatcher = TestCoroutineDispatcher()
        val fetchUsers = FetchUsersUseCase(FakeUsersApi)
        val query = "Ervin"
        val savedStateHandle = SavedStateHandle().apply {
            set("query", query)
        }

        val viewModel = UsersViewModel(dispatcher, savedStateHandle, fetchUsers)
        val state = viewModel.state.value

        assertTrue(state is UsersState.Users)
        assertUsersStateContainsOnlySearchedUsers(state, query)
    }

    private fun assertUsersStateContainsOnlySearchedUsers(
        state: UsersState.Users,
        query: String,
    ) {
        assertTrue(state.users.all { it.name.contains(query, ignoreCase = true) })
        assertTrue(state.users.none { !it.name.contains(query, ignoreCase = true) })
    }
}
