package com.example.androidtdd.users.di

import com.example.androidtdd.users.api.fake.FakeUsersApi
import com.example.androidtdd.users.api.UsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object UsersModule {

    @Provides
    fun dispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    fun usersApi(): UsersApi = FakeUsersApi
}