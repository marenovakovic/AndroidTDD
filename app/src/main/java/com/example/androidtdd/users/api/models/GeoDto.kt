package com.example.androidtdd.users.api.models

import kotlinx.serialization.Serializable

@Serializable
data class GeoDto(
    val lat: String,
    val lng: String,
)
