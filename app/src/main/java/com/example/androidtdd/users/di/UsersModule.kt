package com.example.androidtdd.users.di

import com.example.androidtdd.network.httpClient
import com.example.androidtdd.users.api.UsersApi
import com.example.androidtdd.users.api.UsersApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object UsersModule {

    @Provides
    fun dispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    fun httpClient(): HttpClient = httpClient

    @Provides
    fun usersApi(httpClient: HttpClient): UsersApi = UsersApiImpl(httpClient)
}