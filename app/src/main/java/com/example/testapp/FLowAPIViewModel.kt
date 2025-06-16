package com.example.testapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class FLowAPIViewModel: ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    fun onSearchQueryChanged(newQuery: String) {
            _query.value = newQuery
    }

    val searchResults: Flow<List<String>> = _query
        .debounce(300)//Wait for 300Ms pause before continuing (emits only after pause)
        .distinctUntilChanged() // Avoids redundant API calls
        .filter { it.isNotBlank() }
        .flatMapLatest { query -> // Cancel previous search if a new query comes in
            performSearch(query)
        }

    private fun performSearch(query: String): Flow<List<String>> = flow {

        delay(500) // Simulate network delay
        emit(listOf("Result for \"$query\" 1", "Result for \"$query\" 2"))
    }
}