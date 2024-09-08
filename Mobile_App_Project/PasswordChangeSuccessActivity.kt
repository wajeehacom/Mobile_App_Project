package com.example.financepal



import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class PasswordChangeSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_change_success) // Ensure you have this layout
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}