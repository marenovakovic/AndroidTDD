package com.example.androidtdd.users.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val latitude: String,
    val longitude: String,
) : Parcelable
