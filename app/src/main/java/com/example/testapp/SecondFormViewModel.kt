package com.example.testapp

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SecondFormViewModel : ViewModel() {
    private val _formState = MutableStateFlow(FormData())
    val formState: StateFlow<FormData> = _formState

    fun updateName(name: String) {
        _formState.value = _formState.value.copy(name = name)
    }

    fun updateEmail(email: String) {
        _formState.value = _formState.value.copy(email = email)
    }

    fun saveForm() {
        val data = _formState.value
        Log.d("FormViewModel", "Saved: ${data.name}, ${data.email}")
    }
}


data class FormData(val name: String = "", val email: String = "")