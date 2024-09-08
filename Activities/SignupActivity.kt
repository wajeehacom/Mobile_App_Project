
package com.example.financepal
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.financepal.DatabaseHelper
import com.example.financepal.LoginActivity
import com.example.financepal.R

class SignupActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        dbHelper = DatabaseHelper(this)

        val fullName: EditText = findViewById(R.id.fullName)
        val email: EditText = findViewById(R.id.email)
        val password: EditText = findViewById(R.id.password)
        val confirmPassword: EditText = findViewById(R.id.confirmPassword)
        val signupButton: Button = findViewById(R.id.signupButton)
        val passwordEyeIcon: ImageView = findViewById(R.id.eyeIcon)
        val confirmPasswordEyeIcon: ImageView = findViewById(R.id.confirmPasswordEyeIcon)
        val loginLink: TextView = findViewById(R.id.loginLink)

        passwordEyeIcon.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                passwordEyeIcon.setImageResource(R.drawable.eye_pass) // Open eye icon
            } else {
                password.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordEyeIcon.setImageResource(R.drawable.eye_pass) // Closed eye icon
            }
            password.setSelection(password.text.length)
        }

        confirmPasswordEyeIcon.setOnClickListener {
            isConfirmPasswordVisible = !isConfirmPasswordVisible
            if (isConfirmPasswordVisible) {
                confirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                confirmPasswordEyeIcon.setImageResource(R.drawable.eye_pass) // Open eye icon
            } else {
                confirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                confirmPasswordEyeIcon.setImageResource(R.drawable.eye_pass) // Closed eye icon
            }
            confirmPassword.setSelection(confirmPassword.text.length)
        }

        signupButton.setOnClickListener {
            val name = fullName.text.toString()
            val userEmail = email.text.toString()
            val pass = password.text.toString()
            val confirmPass = confirmPassword.text.toString()

            when {
                name.isEmpty() || userEmail.isEmpty() -> {
                    Toast.makeText(this, "Name and email must not be empty", Toast.LENGTH_SHORT).show()
                }
                pass.length < 8 -> {
                    Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
                }
                pass != confirmPass -> {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    dbHelper.addUser(userEmail, pass)
                    Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, IntroActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        loginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
