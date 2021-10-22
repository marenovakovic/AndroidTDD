package com.example.androidtdd.users.api.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val name: String,
    val email: String?,
    val username: String,
    val address: AddressDto,
    val phone: String,
    val website: String,
    val company: CompanyDto,
)
