package com.example.androidtdd.users.api.models

import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    val geo: GeoDto,
    val zipcode: String,
    val city: String,
    val street: String,
    val suite: String,
)
