

package com.example.financepal


import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var userName: TextView
    private lateinit var userId: TextView
    private lateinit var rowEditProfile: LinearLayout
    private lateinit var rowSettings: LinearLayout
    private lateinit var rowLogout: LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // Initialize views
        profileImage = findViewById(R.id.profileImage)
        userName = findViewById(R.id.userName)
        userId = findViewById(R.id.userId)
        rowEditProfile = findViewById(R.id.rowEditProfile)
        rowSettings = findViewById(R.id.rowSettings)
        rowLogout = findViewById(R.id.rowLogout)


        val homeIcon: ImageView = findViewById(R.id.imageViewIcon1)

        // Set an OnClickListener on the ImageView
        homeIcon.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        val categoryIcon: ImageView = findViewById(R.id.imageViewIcon4)
        categoryIcon.setOnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
        }
        val profileIcon: ImageView = findViewById(R.id.imageViewIcon5)

        // Set an OnClickListener on the ImageView
        profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        // Handle Transaction icon click
        val transactionIcon = findViewById<ImageView>(R.id.imageViewIcon3)
        transactionIcon.setOnClickListener {
            val intent = Intent(this, TransactionActivity::class.java)
            startActivity(intent)
        }
        // Load profile data
        loadProfileData()

        // Set listeners
        rowEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_EDIT_PROFILE)
        }

        rowSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_EDIT_PROFILE)
            // Handle settings click
        }

        rowLogout.setOnClickListener{
            val intent = Intent(this, LogoutActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_EDIT_PROFILE)



        }





    }



    private fun loadProfileData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)
        val userNameStr = sharedPreferences.getString("userName", "Default Name") ?: "Default Name"
        val imagePath = sharedPreferences.getString("imagePath", null)

        userName.text = userNameStr

        if (imagePath != null) {
            val imageUri = Uri.parse(imagePath)
            try {
                val imageStream: InputStream? = contentResolver.openInputStream(imageUri)
                val selectedImage: Bitmap = BitmapFactory.decodeStream(imageStream)
                profileImage.setImageBitmap(selectedImage)
            } catch (e: Exception) {
                e.printStackTrace()
                profileImage.setImageResource(R.drawable.defualt_profile_image) // Placeholder image
            }
        } else {
            profileImage.setImageResource(R.drawable.defualt_profile_image) // Placeholder image
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_EDIT_PROFILE) {
            loadProfileData() // Reload profile data
        }
    }


    companion object {
        private const val REQUEST_CODE_EDIT_PROFILE = 1
    }
}
