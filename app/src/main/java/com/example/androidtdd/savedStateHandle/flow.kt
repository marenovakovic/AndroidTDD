package com.example.androidtdd.savedStateHandle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow

fun <T> SavedStateHandle.flow(key: String, initialValue: T) =
    getLiveData(key, initialValue).asFlow()