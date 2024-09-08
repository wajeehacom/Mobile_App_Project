package com.example.financepal
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.financepal.LoginActivity
import com.example.financepal.R

class LogoutActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private val PREFERENCES_NAME = "UserPrefs"
    private val KEY_SESSION_TOKEN = "sessionToken"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout) // Ensure you have a layout file for this activity

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun onDeleteAccountClick1(view: View) {
        // Handle logout logic here
        clearSessionToken()

        // Start the LoginActivity or any other activity you want after logout
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        // Optionally, finish the current activity
        finish()
    }

    private fun clearSessionToken() {
        val editor = sharedPreferences.edit()
        editor.remove(KEY_SESSION_TOKEN)
        editor.apply() // Commit changes
    }

    fun onCancelSessionClick(view: View) {
        // Finish the current activity to go back to the previous one (without logging out)
        finish()
    }
}
