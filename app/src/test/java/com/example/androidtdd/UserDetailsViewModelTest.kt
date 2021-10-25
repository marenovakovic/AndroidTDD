package com.example.androidtdd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.androidtdd.usecases.invoke
import com.example.androidtdd.userDetails.presentation.UserDetailsState.UserDetailsState
import com.example.androidtdd.userDetails.presentation.UserDetailsViewModel
import com.example.androidtdd.userDetails.usecases.FetchUserUseCase
import com.example.androidtdd.users.api.fake.FakeUsersApi
import com.example.androidtdd.users.usecases.FetchUsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class)
class UserDetailsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val fetchUsers = FetchUsersUseCase(FakeUsersApi)
    private val fetchUser = FetchUserUseCase(FakeUsersApi)

    @Before
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `should emit user with userId passed to it`() = runBlockingTest {
        val user = fetchUsers().first()
        val savedStateHandle = SavedStateHandle().apply {
            set("userId", user.id)
        }
        val viewModel = UserDetailsViewModel(savedStateHandle, fetchUser, TestCoroutineDispatcher())

        val states = mutableListOf<UserDetailsState>()
        val job = launch {
            viewModel.state.toList(states)
        }

        assertSame(states[0], UserDetailsState.Loading)
        assertTrue(states[1] is UserDetailsState.UserDetails)
        assertEquals((states[1] as UserDetailsState.UserDetails).user, user)

        job.cancel()
    }
}