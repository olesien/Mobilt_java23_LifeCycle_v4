package edu.linus.lifecycle

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            val mailField = findViewById<EditText>(R.id.mailField)
            val passwordField = findViewById<EditText>(R.id.passwordField)
            val button = findViewById<Button>(R.id.button)
            val loginBtn = findViewById<TextView>(R.id.login)
            val db = Firebase.firestore
            // Create a new user with a first and last name
            val user = hashMapOf(
                "first" to "Ada",
                "last" to "Lovelace",
                "born" to 1815,
            )

            button.setOnClickListener { view ->
                Log.i(
                    "Test",
                    String.format(
                        "Login attempt... Mail: %s Password: %s",
                        mailField.text,
                        passwordField.text
                    )
                )

                // Add a new document with a generated ID
                db.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d("Test", "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Test", "Error adding document", e)
                    }

                //startActivity(Intent(this, User::class.java))
            }
            loginBtn.setOnClickListener { view ->
                startActivity(Intent(this, MainActivity::class.java))
            }
            insets


        }
    }
}