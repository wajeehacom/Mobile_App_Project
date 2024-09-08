package com.example.financepal

import UserProfile
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream

class EditProfileActivity : AppCompatActivity() {

    private lateinit var profileImage: ImageView
    private lateinit var userId: TextView
    private lateinit var username: EditText

    private lateinit var phoneNumber: EditText
    private lateinit var emailAddress: EditText
    private lateinit var updateProfileButton: Button

    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // Initialize views
        profileImage = findViewById(R.id.profileImage)

        username = findViewById(R.id.username)

        phoneNumber = findViewById(R.id.phoneNumber)
        emailAddress = findViewById(R.id.emailAddress)
        updateProfileButton = findViewById(R.id.updateProfileButton)

        // Set listeners
        profileImage.setOnClickListener {
            openImagePicker()
        }

        updateProfileButton.setOnClickListener {
            updateProfile()
        }
    }

    private fun openImagePicker() {
        // Launch intent to pick an image from the gallery
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            selectedImageUri = data?.data
            val imageStream: InputStream? = selectedImageUri?.let { contentResolver.openInputStream(it) }
            val selectedImage: Bitmap = BitmapFactory.decodeStream(imageStream)
            profileImage.setImageBitmap(selectedImage)
        }
    }



    private fun updateProfile() {
        // Collect input data
        val userName = username.text.toString()
        val phone = phoneNumber.text.toString()
        val email = emailAddress.text.toString()
        val imagePath = selectedImageUri?.toString() ?: "" // Handle null case

        // Create a UserProfile object
        val userProfile = UserProfile(0, userName, phone, email, imagePath)

        // Save to database
        val databaseHelper = DatabaseHelper(this)
        databaseHelper.insertUserProfile(userProfile)

        // Save data to SharedPreferences
        val sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userName", userName)
        editor.putString("phone", phone)
        editor.putString("email", email)
        editor.putString("imagePath", imagePath) // Save the image URI
        editor.apply()

        // Show success message
        Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
        setResult(Activity.RESULT_OK) // Set result to inform the previous activity
        finish() // Close the activity
    }





}
