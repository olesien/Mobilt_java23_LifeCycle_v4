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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class User : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser
    private lateinit var nameField: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var gender: RadioGroup
    private lateinit var receiveUpdates: CheckBox
    private lateinit var personNumber: EditText
    private var documentId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_user)
         auth = Firebase.auth
         val db = Firebase.firestore
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            //Get fields
            nameField = findViewById(R.id.nameField)
            phoneNumber = findViewById(R.id.phoneField)
            val logout = findViewById<FloatingActionButton>(R.id.logout)
            val saveData = findViewById<Button>(R.id.button)
            gender = findViewById(R.id.radioGender)
            receiveUpdates = findViewById(R.id.receiveUpdates)
            personNumber = findViewById(R.id.personNumberField)

            //Logout
            logout.setOnClickListener { view ->
                Firebase.auth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(
                    baseContext,
                    "Successfully logged out.",
                    Toast.LENGTH_SHORT,
                ).show()
            }

            saveData.setOnClickListener {
                if (documentId.isEmpty()) {
                    Log.i("TAG", "documentId is empty")
                    return@setOnClickListener
                }
                val updates = hashMapOf<String, Any>(
                    "name" to nameField.text.toString(),
                    "phone_number" to phoneNumber.text.toString().toInt(),
                    "gender" to findViewById<RadioButton>(gender.checkedRadioButtonId).text.toString(),
                    "receive_updates" to receiveUpdates.isChecked,
                    "person_number" to personNumber.text.toString().toInt()
                )

                db.collection("users")
                    .document(documentId)
                    .update(updates)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Successfully saved data!", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w("Test", "Error saving document", e)
                    }
            }

            insets


        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = auth.currentUser!!
        //We have a user
        val db = Firebase.firestore
        //                // Get user
        db.collection("users")
            .whereEqualTo("mail", currentUser.email)
            .get()
            .addOnSuccessListener { documents ->
                Log.d("Test", "Account found!")

                //Fill data in
                val first = documents.first()
                if (first !== null) {
                    documentId = first.id
                    val data = first.data;
                    Log.d("TAG", "$data")

                    nameField.setText(data["name"].toString())
                    phoneNumber.setText(data["phone_number"].toString())
                    //Get radio button
                    var selected: Int? = null;
                    when (data["gender"].toString()) {
                        "Male" -> selected = R.id.radioGenderMale
                        "Female" -> selected = R.id.raidoGenderFemale
                        "Other" -> selected = R.id.radioGenderOther
                        else -> {
                            Log.i("TAG", "Gender not found. Assuming attack helicopter ")
                        }
                    }
                    if (selected != null) {
                        gender.check(selected)
                    }

                    receiveUpdates.isChecked = data["receive_updates"].toString().toBoolean()
                    personNumber.setText(data["person_number"].toString())
                }
            }
            .addOnFailureListener { e ->
                Log.w("Test", "Error retrieving document", e)
            }
    }
}