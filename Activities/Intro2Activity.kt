package com.example.financepal

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Intro2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro2)

        // Set the activity to fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Find the Next button by ID
        val nextButton: TextView = findViewById(R.id.nextButton)

        // Handle button click
        nextButton.setOnClickListener {
            // Navigate to the next activity
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        }

        // If you want to handle the overlay image, you can do this:
        val overlayImageView: ImageView = findViewById(R.id.overlayImageView)
        // You can set actions on overlayImageView if needed
    }
}
