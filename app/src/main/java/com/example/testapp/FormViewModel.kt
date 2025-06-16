package com.example.testapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FormViewModel: ViewModel() {
    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
}