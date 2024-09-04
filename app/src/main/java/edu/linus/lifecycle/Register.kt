package edu.linus.lifecycle

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
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
            val nameField = findViewById<EditText>(R.id.nameField)
            val mailField = findViewById<EditText>(R.id.mailField)
            val passwordField = findViewById<EditText>(R.id.passwordField)
            val passwordField2 = findViewById<EditText>(R.id.passwordField2)
            val phoneNumber = findViewById<EditText>(R.id.phoneField)
            val gender = findViewById<RadioGroup>(R.id.radioGender)
            val receiveUpdates = findViewById<CheckBox>(R.id.receiveUpdates)
            val errText = findViewById<TextView>(R.id.err)
            val button = findViewById<Button>(R.id.button)
            val loginBtn = findViewById<TextView>(R.id.login)
            val db = Firebase.firestore

            button.setOnClickListener { view ->
                errText.text = "";

                if (passwordField.text.equals(passwordField2.text)) {
                    //Early return due to mismatch in passwords
                    Log.i("TAGPASS", String.format("1: %s 2: %s", passwordField.text, passwordField2.text))
                    errText.text = "Password fields do not match";
                    return@setOnClickListener;
                }

                //Validation passed
                // Create a new user with a first and last name
                val user = hashMapOf(
                    "name" to nameField.text.toString(),
                    "mail" to mailField.text.toString(),
                    "password" to passwordField.text.toString(),
                    "phoneNumber" to phoneNumber.text.toString().toInt(),
                    "gender" to findViewById<RadioButton>(gender.checkedRadioButtonId).text.toString(),
                    "receive_updates" to receiveUpdates.isChecked
                )

                Log.i(
                    "Test",
                    String.format(
                        "Register attempt... Name: %s Mail: %s Password: %s Phone: %d Gender: %s Updates: %b",
                        nameField.text.toString(),
                        mailField.text.toString(),
                        passwordField.text.toString(),
                        phoneNumber.text.toString().toInt(),
                        findViewById<RadioButton>(gender.checkedRadioButtonId).text.toString(),
                        receiveUpdates.isChecked
                    )
                )

                //Check if mail already exists


                // Add a new document with a generated ID
                db.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d("Test", "DocumentSnapshot added with ID: ${documentReference.id}")
                        Toast.makeText(this, "Successfully registered!", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, User::class.java))
                    }
                    .addOnFailureListener { e ->
                        Log.w("Test", "Error adding document", e)
                    }
                Log.w("Test", "Added doc???")

            }
            loginBtn.setOnClickListener { view ->
                startActivity(Intent(this, MainActivity::class.java))
            }
            insets


        }
    }
}