package com.example.androidtdd.users.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val email: String?,
    val userName: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company,
) : Parcelable
