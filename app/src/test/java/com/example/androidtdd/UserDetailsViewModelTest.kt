package com.example.androidtdd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.androidtdd.usecases.invoke
import com.example.androidtdd.userDetails.presentation.UserDetailsState.UserDetailsState
import com.example.androidtdd.userDetails.presentation.UserDetailsViewModel
import com.example.androidtdd.userDetails.usecases.FetchUserUseCase
import com.example.androidtdd.users.api.fake.FakeUsersApi
import com.example.androidtdd.users.usecases.FetchUsersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
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

    @OptIn(ExperimentalTime::class)
    @Test
    fun `should emit user with userId passed to it`() = runBlockingTest {
        val user = fetchUsers().first()
        val savedStateHandle = SavedStateHandle().apply {
            set("userId", user.id)
        }
        val viewModel = UserDetailsViewModel(savedStateHandle, fetchUser, TestCoroutineDispatcher())

        viewModel.state.test {
            assertSame(UserDetailsState.Loading, awaitItem())
            val state = awaitItem()
            assertTrue(state is UserDetailsState.UserDetails)
            assertEquals((state as UserDetailsState.UserDetails).user, user)
        }
    }
}