package com.example.financepal


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class PasswordSettingsActivity : AppCompatActivity() {

    private lateinit var currentPasswordField: EditText
    private lateinit var newPasswordField: EditText
    private lateinit var confirmNewPasswordField: EditText
    private lateinit var changePasswordButton: Button

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_settings)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dbHelper = DatabaseHelper(this)
        sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)

        currentPasswordField = findViewById(R.id.currentPassword)
        newPasswordField = findViewById(R.id.newPassword)
        confirmNewPasswordField = findViewById(R.id.confirmNewPassword)
        changePasswordButton = findViewById(R.id.changePasswordButton)

        changePasswordButton.setOnClickListener {
            val currentPassword = currentPasswordField.text.toString()
            val newPassword = newPasswordField.text.toString()
            val confirmNewPassword = confirmNewPasswordField.text.toString()

            if (newPassword == confirmNewPassword) {
                val username = sharedPreferences.getString("userName", null) // Retrieve username from SharedPreferences
                if (username != null) {
                    val success = dbHelper.updatePassword(username, currentPassword, newPassword)
                    if (success) {
                        // Password changed successfully
                        Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()

                        // Start the success activity
                        val intent = Intent(this, PasswordChangeSuccessActivity::class.java)
                        startActivity(intent)

                        // Optionally, finish this activity
                        finish()
                    } else {
                        Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
