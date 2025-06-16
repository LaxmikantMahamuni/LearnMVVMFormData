package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * Use of StateFlow to store Form data in viewmodel and survive the data in config change(screen rotation)
 */
class LearnFlowAPIActivity : AppCompatActivity() {

    lateinit var flowAPIViewModel: FLowAPIViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_api)


        flowAPIViewModel = FLowAPIViewModel()

        val editTextSearch = findViewById<EditText>(R.id.editTextSearch)
        val textViewResult = findViewById<TextView>(R.id.textViewSearchResult)
        val buttonSubmit = findViewById<Button>(R.id.submit_button)

        editTextSearch.addTextChangedListener {
            flowAPIViewModel.onSearchQueryChanged(it.toString())
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flowAPIViewModel.searchResults.collect { result ->
                    textViewResult.text = result.joinToString("\n")
                }
            }
        }

        buttonSubmit.setOnClickListener {
            Toast.makeText(this, "Do Something!", Toast.LENGTH_SHORT).show()
        }
    }
}

