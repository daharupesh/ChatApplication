package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// Sign-up activity for user registration
class SignUP : AppCompatActivity() {

    // UI elements
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var editName: EditText
    private lateinit var btnSignUp: Button

    // Firebase authentication and database references
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        // Initialize Firebase authentication
        mAuth = FirebaseAuth.getInstance()

        // Reference UI elements
        editEmail = findViewById(R.id.edt_email)
        editPassword = findViewById(R.id.edt_password)
        editName = findViewById(R.id.edt_name)
        btnSignUp = findViewById(R.id.btnSigUp)

        // Set up click listener for sign-up button
        btnSignUp.setOnClickListener {
            // Retrieve user input
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            // Check if any field is empty
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showToast("All fields are required")
            } else {
                // Call sign-up method if fields are not empty
                signUp(name, email, password)
            }
        }
    }

    // Function to handle user sign-up
    private fun signUp(name: String, email: String, password: String) {
        // Create user in Firebase authentication
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Add user to Firebase database
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)

                    // Navigate to login screen after successful sign-up
                    val intent = Intent(this@SignUP, Login::class.java)
                    finish()
                    startActivity(intent)

                    showToast("Sign Up successfully")
                } else {
                    // Display error message if sign-up fails
                    showToast("Some error occurred")
                }
            }
    }

    // Function to add user data to Firebase database
    private fun addUserToDatabase(name: String, email: String, uid: String) {
        // Initialize Firebase database reference
        mDbRef = FirebaseDatabase.getInstance().getReference()
        // Add user data to the "user" node with a unique UID
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))
    }

    // Function to display a toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}



