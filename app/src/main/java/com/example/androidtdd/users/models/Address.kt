package com.example.androidtdd.users.models

data class Address(
    val location: Location,
    val zipCode: String,
    val city: String,
    val street: String,
    val suite: String,
)
