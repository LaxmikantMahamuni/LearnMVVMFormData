package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * Use of StateFlow to store Form data in viewmodel and survive the data in config change(screen rotation)
 */
class SecondActivity : AppCompatActivity() {

    lateinit var secondFormViewModel: SecondFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        secondFormViewModel = SecondFormViewModel()

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextEmailAddress = findViewById<EditText>(R.id.editTextEmailAddress)
        val buttonSubmit = findViewById<Button>(R.id.submit_button)
        val buttonNext = findViewById<Button>(R.id.submit_next)

        editTextName.addTextChangedListener {
            secondFormViewModel.updateName(it.toString())
        }

        editTextEmailAddress.addTextChangedListener {
            secondFormViewModel.updateEmail(it.toString())
        }

        buttonSubmit.setOnClickListener {
            secondFormViewModel.saveForm()
            Toast.makeText(this, "Form Saved!", Toast.LENGTH_SHORT).show()
        }

        buttonNext.setOnClickListener {
            this.startActivity(Intent(MainActivity@ this, LearnFlowAPIActivity::class.java))
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                secondFormViewModel.formState.collect { form ->

                    if (editTextName.text.toString() != form.name)
                        editTextName.setText(form.name)

                    if (editTextEmailAddress.text.toString() != form.email)
                        editTextEmailAddress.setText(form.email)

                }
            }
        }


        lifecycleScope.launch {
            getNumbers().collect {
                println("Number: $it")
            }
        }
    }

    fun getNumbers(): Flow<Int> = flow {
        for (i in 1..5) {
            delay(1000)
            emit(i)
        }
    }
}

