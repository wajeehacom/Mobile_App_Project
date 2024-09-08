package com.example.financepal

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.UUID

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private val PREFERENCES_NAME = "UserPrefs"
    private val KEY_SESSION_TOKEN = "sessionToken"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

        val username: EditText = findViewById(R.id.username)
        val password: EditText = findViewById(R.id.password)
        val loginButton: Button = findViewById(R.id.btnLogin)
        val dontHaveAccount: TextView = findViewById(R.id.signUpLink)
        val eyeIcon: ImageView = findViewById(R.id.eyeIcon)

        var isPasswordVisible = false

        loginButton.setOnClickListener {
            val enteredUsername = username.text.toString()
            val enteredPassword = password.text.toString()

            if (validateLoginInput(enteredUsername, enteredPassword)) {
                if (checkUser(enteredUsername, enteredPassword)) {
                    val sessionToken = UUID.randomUUID().toString()
                    saveSessionToken(sessionToken)

                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Username and password must not be empty", Toast.LENGTH_SHORT).show()
            }
        }

        dontHaveAccount.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        eyeIcon.setOnClickListener {
            if (isPasswordVisible) {
                // Hide password
                password.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                eyeIcon.setImageResource(R.drawable.eye_pass) // Replace with your eye closed drawable
            } else {
                // Show password
                password.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                eyeIcon.setImageResource(R.drawable.eye_pass) // Replace with your eye open drawable
            }
            password.setSelection(password.text.length) // Move cursor to the end
            isPasswordVisible = !isPasswordVisible
        }
    }

    private fun saveSessionToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_SESSION_TOKEN, token)
        editor.apply()
    }

    private fun checkUser(username: String, password: String): Boolean {
        // Implement user check logic here
        return true // For demonstration purposes
    }

    private fun validateLoginInput(username: String, password: String): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }
}
