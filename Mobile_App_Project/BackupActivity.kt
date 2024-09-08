    package com.example.financepal

    import Transaction
    import android.os.Bundle
    import android.util.Log
    import android.view.WindowManager
    import android.widget.Button
    import android.widget.ImageView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import com.google.gson.GsonBuilder
    import kotlinx.coroutines.GlobalScope
    import kotlinx.coroutines.launch
    import retrofit2.HttpException
    import retrofit2.Response
    import java.io.IOException

    class BackupActivity : AppCompatActivity() {

        private lateinit var backupButton: Button
        private lateinit var sqliteHelper: MySQLiteHelper

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_backup)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            val backArrow = findViewById<ImageView>(R.id.imageViewBackArrow)
            backArrow.setOnClickListener {
                // Handle back navigation
                onBackPressed()
            }
            backupButton = findViewById(R.id.buttonBackup)
            sqliteHelper = MySQLiteHelper(this)
            val transactions = getTransactionsToBackup()
            backupButton.setOnClickListener {
                // Fetch the transactions to backup
                GlobalScope.launch {
                    try {
                        Log.d("BackupActivity", "Transactions to backup: ${GsonBuilder().create().toJson(transactions)}")
                        // Send data using Retrofit
                        val response = RetrofitInstance.api.backupTransactions(transactions)
                        Log.d("BackupActivity", "API response code: ${response.code()}")

                        if (response.isSuccessful) {
                            runOnUiThread {
                                Toast.makeText(
                                    this@BackupActivity,
                                    "Transactions added successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(
                                    this@BackupActivity,
                                    "Failed to add transactions: ${response.message()}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } catch (e: HttpException) {
                        runOnUiThread {
                            Toast.makeText(
                                this@BackupActivity,
                                "HTTP error: ${e.code()} ${e.message()}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: IOException) {
                        runOnUiThread {
                            Toast.makeText(
                                this@BackupActivity,
                                "Network error: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {
                        Log.d("BackupActivity",  e.toString())
                        runOnUiThread {
                            Toast.makeText(
                                this@BackupActivity,
                                "Error sending data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        private fun getTransactionsToBackup(): List<Transaction> {
            // Retrieve transactions from SQLite database
            return sqliteHelper.getAllExpenses()
        }


        private fun showToast(message: String) {
            runOnUiThread {
                Toast.makeText(this@BackupActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
