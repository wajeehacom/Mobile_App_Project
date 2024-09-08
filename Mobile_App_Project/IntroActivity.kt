package com.example.financepal

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // Find views by ID
        val nextButton: TextView = findViewById(R.id.nextButton)

        // Handle button click
        nextButton.setOnClickListener {
            // Navigate to the next activity
            val intent = Intent(this, Intro2Activity::class.java) // Replace with your actual next activity
            startActivity(intent)
        }


    }
}
