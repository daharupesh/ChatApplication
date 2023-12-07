package com.example.chatapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    // UI elements
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth // For initializing Firebase connection

    private var isSplashScreenShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // logic for open splace screen when only start the app
        if (!isSplashScreenShown) {
            Thread.sleep(1500)
            installSplashScreen()
            isSplashScreenShown = true
        }

        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance() // Initialize Firebase authentication

        // Reference UI elements
        editEmail = findViewById(R.id.edt_email)
        editPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSigUp)


        // SignUp button
        btnSignUp.setOnClickListener {
            // Create an Intent to navigate to the SignUP activity
            val intent = Intent(this, SignUP::class.java)
            // Start the activity
            startActivity(intent)
        }


        // Login button
        btnLogin.setOnClickListener {
            // Retrieve user input
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            // Check if any field is empty
            if (email.isEmpty() || password.isEmpty()) {
                // Show an error toast if fields are empty
                showToast("Please fill in all fields.")
            } else {
                // Call the login method if fields are not empty
                login(email, password)
            }
        }
    }

    // ... Rest of your code ...

    // Method to handle user login
    private fun login(email: String, password: String) {
        // Logic for login user...

        // Attempt to sign in with email and password
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Code for logging in the user
                    // Create an Intent to navigate to the MainActivity
                    val intent = Intent(this@Login, MainActivity::class.java)
                    // Finish the current activity and start the MainActivity
                    finish()
                    startActivity(intent)

                    // Show a success toast message
                    showToast("Login successfully!")
                } else {
                    // Show an error toast message
                    showToast("Login failed. User does not exist.")
                }
            }
    }

    // Function to display a toast message
    private fun showToast(message: String) {
        // Create and show a toast message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
