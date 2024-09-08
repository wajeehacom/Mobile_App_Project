//package com.example.financepal
//import UserProfile
//import android.annotation.SuppressLint
//import android.content.ContentValues
//import android.content.Context
//import android.database.Cursor
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//
//class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
//
//    companion object {
//        private const val DATABASE_NAME = "ExpensesDatabase"
//        private const val DATABASE_VERSION = 3 // Increment the version number if you make changes
//        private const val TABLE_USERS = "User"
//        private const val COLUMN_ID = "id"
//        private const val COLUMN_USERNAME = "username"
//        private const val COLUMN_PASSWORD = "password"
//        private const val COLUMN_PHONE = "phone"
//        private const val COLUMN_EMAIL = "email"
//        private const val COLUMN_IMAGE_PATH = "imagePath"
//    }
//
//    override fun onCreate(db: SQLiteDatabase?) {
//        // Create the single users table with all required columns
//        val createUsersTable = """
//            CREATE TABLE $TABLE_USERS (
//                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
//                $COLUMN_USERNAME TEXT UNIQUE,
//                $COLUMN_PASSWORD TEXT,
//                $COLUMN_PHONE TEXT,
//                $COLUMN_EMAIL TEXT,
//                $COLUMN_IMAGE_PATH TEXT
//            )
//        """.trimIndent()
//        db?.execSQL(createUsersTable)
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
//        onCreate(db)
//    }
//
//    // Add a user to the database
//
//    // Add a user to the database
//    fun addUser(username: String, password: String): Long {
//        val db = this.writableDatabase
//        val values = ContentValues().apply {
//            put(COLUMN_USERNAME, username)
//            put(COLUMN_PASSWORD, password)
//        }
//        return db.insert(TABLE_USERS, null, values)
//    }
//
//
//    // Delete a user account
//    fun deleteUserAccount(username: String): Boolean {
//        val db = this.writableDatabase
//        val rowsDeleted = db.delete(TABLE_USERS, "$COLUMN_USERNAME = ?", arrayOf(username))
//        return rowsDeleted > 0
//    }
//    // Insert a user profile into the database
//    fun insertUserProfile(userProfile: UserProfile): Long {
//        val db = this.writableDatabase
//        val values = ContentValues().apply {
//            put(COLUMN_USERNAME, userProfile.userName)
//            put(COLUMN_PHONE, userProfile.phone)
//            put(COLUMN_EMAIL, userProfile.email)
//            put(COLUMN_IMAGE_PATH, userProfile.imagePath)
//        }
//        return db.insert(TABLE_USERS, null, values)
//    }
//
//
//    // Check if the user exists in the database
//    fun checkUser(username: String, password: String): Boolean {
//        val db = this.readableDatabase
//        val cursor: Cursor = db.query(
//            TABLE_USERS,
//            arrayOf(COLUMN_ID),
//            "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?",
//            arrayOf(username, password),
//            null,
//            null,
//            null
//        )
//        val exists = cursor.count > 0
//        cursor.close()
//        return exists
//    }
//
//    // Update the password for a user
//
//
//    fun updatePassword(username: String, currentPassword: String, newPassword: String): Boolean {
//        val db = writableDatabase
//        val query = "SELECT $COLUMN_PASSWORD FROM $TABLE_USERS WHERE $COLUMN_USERNAME = ?"
//        val cursor = db.rawQuery(query, arrayOf(username))
//
//        return if (cursor.moveToFirst()) {
//            val storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
//            if (storedPassword == currentPassword) {
//                val values = ContentValues().apply {
//                    put(COLUMN_PASSWORD, newPassword)
//                }
//                val rowsAffected = db.update(TABLE_USERS, values, "$COLUMN_USERNAME = ?", arrayOf(username))
//                cursor.close()
//                rowsAffected > 0 // Return true if the password was successfully updated
//            } else {
//                cursor.close()
//                false // Current password is incorrect
//            }
//        } else {
//            cursor.close()
//            false // User not found
//        }
//    }
//    // Retrieve user profile by username
//
//
//    @SuppressLint("Range")
//    fun getUserProfile(username: String): UserProfile? {
//        val db = this.readableDatabase
//        val cursor: Cursor = db.query(
//            TABLE_USERS,
//            arrayOf(COLUMN_ID, COLUMN_USERNAME, COLUMN_PHONE, COLUMN_EMAIL, COLUMN_IMAGE_PATH, COLUMN_PASSWORD),
//            "$COLUMN_USERNAME = ?",
//            arrayOf(username),
//            null,
//            null,
//            null
//        )
//
//        return if (cursor.moveToFirst()) {
//            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
//            val phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
//            val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
//            val imagePath = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH))
//            val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
//            cursor.close()
//            UserProfile(id, username, phone, email, imagePath, password)
//        } else {
//            cursor.close()
//            null
//        }
//    }
//
//}

package com.example.financepal
import UserProfile
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ExpensesDatabase"
        private const val DATABASE_VERSION = 6 // Increment the version number if you make changes
        private const val TABLE_USERS = "Users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_IMAGE_PATH = "imagePath"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create the single users table with all required columns
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT UNIQUE,
                $COLUMN_PASSWORD TEXT,
                $COLUMN_PHONE TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_IMAGE_PATH TEXT
            )
        """.trimIndent()
        db?.execSQL(createUsersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // Add a user to the database

    // Add a user to the database
    fun addUser(username: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
        }
        return db.insert(TABLE_USERS, null, values)
    }


    // Delete a user account
    fun deleteUser(username: String): Boolean {
        val db = writableDatabase
        val result = db.delete("Users", "username=?", arrayOf(username))
        db.close()
        return result > 0
    }

    // Insert a user profile into the database
    fun insertUserProfile(userProfile: UserProfile): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, userProfile.userName)
            put(COLUMN_PHONE, userProfile.phone)
            put(COLUMN_EMAIL, userProfile.email)
            put(COLUMN_IMAGE_PATH, userProfile.imagePath)
        }
        return db.insert(TABLE_USERS, null, values)
    }


    // Check if the user exists in the database
    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID),
            "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(username, password),
            null,
            null,
            null
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    // Update the password for a user


    fun updatePassword(username: String, currentPassword: String, newPassword: String): Boolean {
        val db = writableDatabase
        val query = "SELECT $COLUMN_PASSWORD FROM $TABLE_USERS WHERE $COLUMN_USERNAME = ?"
        val cursor = db.rawQuery(query, arrayOf(username))

        return if (cursor.moveToFirst()) {
            val storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            if (storedPassword == currentPassword) {
                val values = ContentValues().apply {
                    put(COLUMN_PASSWORD, newPassword)
                }
                val rowsAffected = db.update(TABLE_USERS, values, "$COLUMN_USERNAME = ?", arrayOf(username))
                cursor.close()
                rowsAffected > 0 // Return true if the password was successfully updated
            } else {
                cursor.close()
                false // Current password is incorrect
            }
        } else {
            cursor.close()
            false // User not found
        }
    }



    // Retrieve user profile by username


    @SuppressLint("Range")
    fun getUserProfile(username: String): UserProfile? {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID, COLUMN_USERNAME, COLUMN_PHONE, COLUMN_EMAIL, COLUMN_IMAGE_PATH, COLUMN_PASSWORD),
            "$COLUMN_USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
            val email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
            val imagePath = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_PATH))
            val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
            cursor.close()
            UserProfile(id, username, phone, email, imagePath, password)
        } else {
            cursor.close()
            null
        }
    }

}