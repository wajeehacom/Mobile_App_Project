
package com.example.financepal

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DeleteAccountActivity : AppCompatActivity() {

    private lateinit var passwordField: EditText
    private lateinit var btnDelete: Button
    private lateinit var btnCancel: Button

    private lateinit var dbHelper: DatabaseHelper

    // Replace this method with your actual way of fetching the username
    private fun getCurrentUsername(): String {
        // Implement the logic to fetch the current username, e.g., from SharedPreferences or a singleton
        return "current_username" // Placeholder
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_account)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dbHelper = DatabaseHelper(this)

        passwordField = findViewById(R.id.passwordField)
        btnDelete = findViewById(R.id.btnDelete)
        btnCancel = findViewById(R.id.btnCancel)

        btnDelete.setOnClickListener {
            val password = passwordField.text.toString()
            if (password.isNotEmpty()) {
                val username = getCurrentUsername()
                val userProfile = dbHelper.getUserProfile(username)
                if (userProfile != null && userProfile.password == password) {
                    showConfirmationDialog(username)
                } else {
                    Toast.makeText(this, "Password is incorrect", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            finish() // Close the activity
        }
        val backArrow = findViewById<ImageView>(R.id.imageViewBackArrow)
        backArrow.setOnClickListener {
            // Handle back navigation
            onBackPressed()
        }
    }

    private fun showConfirmationDialog(username: String) {
        AlertDialog.Builder(this)
            .setTitle("Delete Account")
            .setMessage("Are you sure you want to delete your account? By deleting your account, you agree that you understand the consequences of this action and that you agree to permanently delete your account and all associated data.")
            .setPositiveButton("Delete") { dialog, _ ->
                deleteUserAccount(username)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun deleteUserAccount(username: String) {
        val success = dbHelper.deleteUser(username)
        if (success) {
            Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error deleting account", Toast.LENGTH_SHORT).show()
        }
    }
}