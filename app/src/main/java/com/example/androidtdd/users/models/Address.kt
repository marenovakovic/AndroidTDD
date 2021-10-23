package com.example.androidtdd.users.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val location: Location,
    val zipCode: String,
    val city: String,
    val street: String,
    val suite: String,
) : Parcelable
