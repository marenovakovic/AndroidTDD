package com.example.androidtdd

import com.example.androidtdd.users.api.UsersApi
import com.example.androidtdd.users.api.fake.FakeUsersApi
import com.example.androidtdd.users.di.UsersModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UsersModule::class],
)
object TestUsersModule {

    @Provides
    fun dispatcher(): CoroutineDispatcher = TestCoroutineDispatcher()

    @Provides
    fun fakeUsersApi(): UsersApi = FakeUsersApi
}