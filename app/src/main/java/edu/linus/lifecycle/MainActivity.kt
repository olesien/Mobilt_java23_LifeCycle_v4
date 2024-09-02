package edu.linus.lifecycle

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                val mailField = findViewById<EditText>(R.id.editTextTextEmailAddress)
                val passwordField = findViewById<EditText>(R.id.editTextTextPassword2)
                val button = findViewById<Button>(R.id.button)
                button.setOnClickListener{view ->
                    Log.i("Test", String.format("Login attempt... Mail: %s Password: %s", mailField.text, passwordField.text))
                }

                insets


        }
    }
}