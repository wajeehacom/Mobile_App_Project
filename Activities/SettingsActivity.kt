package com.example.financepal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var notificationSettingsRow: LinearLayout
    private lateinit var privacySettingsRow: LinearLayout
    private lateinit var deleteAccountRow: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // Initialize views

        privacySettingsRow = findViewById(R.id.privacySettingsRow)
        deleteAccountRow = findViewById(R.id.deleteAccountRow)


        privacySettingsRow.setOnClickListener {
            val intent = Intent(this, PasswordSettingsActivity::class.java)
            startActivity(intent)
        }

        deleteAccountRow.setOnClickListener {
            val intent = Intent(this, DeleteAccountActivity::class.java)
            startActivity(intent)
        }
        fun onDeleteAccountClick(view: View) {
            // Navigate to the AccountDeletionConfirmationActivity
            val intent = Intent(this, AccountDeletionConfirmationActivity::class.java)
            startActivity(intent)
        }

    }
}