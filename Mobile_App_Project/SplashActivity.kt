package com.example.financepal

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private val PREFERENCES_NAME = "UserPrefs"
    private val KEY_SESSION_TOKEN = "sessionToken"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

        val sessionToken = getSessionToken()
        val targetActivity = if (sessionToken != null) HomeActivity::class.java else MainActivity::class.java

        Handler().postDelayed({
            val intent = Intent(this, targetActivity)
            startActivity(intent)
            finish()
        }, 2000) // 2 seconds delay
    }

    private fun getSessionToken(): String? {
        return sharedPreferences.getString(KEY_SESSION_TOKEN, null)
    }
}
