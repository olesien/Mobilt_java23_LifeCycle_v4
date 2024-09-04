package edu.linus.lifecycle

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            val mailField = findViewById<EditText>(R.id.mailField)
            val passwordField = findViewById<EditText>(R.id.passwordField)
            val button = findViewById<Button>(R.id.button)
            val registerBtn = findViewById<TextView>(R.id.register)
            val errText = findViewById<TextView>(R.id.err)
            val db = Firebase.firestore
            // Create a new user with a first and last name

            button.setOnClickListener { view ->
                errText.text = "";
                Log.i(
                    "Test",
                    String.format(
                        "Login attempt... Mail: %s Password: %s",
                        mailField.text,
                        passwordField.text
                    )
                )
                auth.signInWithEmailAndPassword(mailField.text.toString(), passwordField.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success")
                            val user = auth.currentUser
                            startActivity(Intent(this, User::class.java));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
//                // Get user
//                db.collection("users")
//                    .whereEqualTo("mail", mailField.text.toString())
//                    .get()
//                    .addOnSuccessListener { documents ->
//                        Log.d("Test", "Account found!")
//                        Toast.makeText(this, "Successfully logged in!", Toast.LENGTH_LONG).show()
//                        startActivity(Intent(this, User::class.java))
//                    }
//                    .addOnFailureListener { e ->
//                        errText.text = "Account could not be found"
//                        Log.w("Test", "Error adding document", e)
//                    }

                //startActivity(Intent(this, User::class.java))
            }
            registerBtn.setOnClickListener { view ->
                startActivity(Intent(this, Register::class.java))
            }
            insets


        }
    }

    public override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //Redirect to user page
            startActivity(Intent(this, User::class.java))
        }
    }
}