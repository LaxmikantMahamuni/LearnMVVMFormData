package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider

/**
 * Use of LiveData to store Form data in viewmodel and survive the data in config change(screen rotation)
 */
class MainActivity : AppCompatActivity() {

    lateinit var formViewModel: FormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        Log.d("LEARNING", "In Activity --> onCreate()")

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextEmailAddress = findViewById<EditText>(R.id.editTextEmailAddress)

        val factory = FormViewModelFactory()
        formViewModel = ViewModelProvider(this, factory).get(FormViewModel::class.java)

        formViewModel.name.observe(this) {
            if (editTextName.text.toString() != it)
                editTextName.setText(it)
        }
        formViewModel.email.observe(this) {
            if (editTextEmailAddress.text.toString() != it)
                editTextEmailAddress.setText(it)
        }

        editTextName.addTextChangedListener {
            formViewModel.name.value = it.toString()
        }

        editTextEmailAddress.addTextChangedListener {
            formViewModel.email.value = it.toString()
        }

        findViewById<Button>(R.id.submit_button).setOnClickListener {
            Toast.makeText(
                this, "Name = ${formViewModel.name.value}" +
                        " Email = ${formViewModel.email.value}",
                Toast.LENGTH_SHORT
            ).show()
        }

        findViewById<Button>(R.id.next_activity_button).setOnClickListener {
            this.startActivity(Intent(MainActivity@ this, SecondActivity::class.java))
        }
    }
}