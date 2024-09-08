package com.example.financepal

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.financepal.LoginActivity
import com.example.financepal.R
import com.example.financepal.SignupActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var btnSignup: Button
    private lateinit var forgotPasswordLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        btnLogin = findViewById(R.id.btnLogin)
        btnSignup = findViewById(R.id.btnSignup)

        btnLogin.setOnClickListener {
            // Navigate to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnSignup.setOnClickListener {
            // Navigate to SignupActivity
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

    }
}
