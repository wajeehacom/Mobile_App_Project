
package com.example.financepal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class AccountDeletionConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_deletion_confirmation)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // You can set up your UI and logic for confirming the account deletion here
    }
    fun onDeleteAccountClick1(view: View) {
        // Navigate to the AccountDeletionConfirmationActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}