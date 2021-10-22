package com.example.androidtdd.savedStateHandle

import androidx.lifecycle.SavedStateHandle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SavedStateHandleListDelegate<Value>(
    private val key: String,
    private val savedStateHandle: SavedStateHandle,
) : ReadWriteProperty<Any, List<Value>> {
    override fun getValue(thisRef: Any, property: KProperty<*>): List<Value> =
        savedStateHandle[key] ?: emptyList()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: List<Value>) {
        savedStateHandle[key] = value
    }
}