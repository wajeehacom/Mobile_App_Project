package com.example.financepal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CategoriesActivity : AppCompatActivity() {
    private lateinit var mySQLiteHelper: MySQLiteHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // Set onClickListeners for the category boxes
        findViewById<View>(R.id.box1).setOnClickListener { onBoxClick("Food") }
        findViewById<View>(R.id.box2).setOnClickListener { onBoxClick("Transport") }
        findViewById<View>(R.id.box3).setOnClickListener { onBoxClick("Medicine") }
        findViewById<View>(R.id.box4).setOnClickListener { onBoxClick("Groceries") }
        findViewById<View>(R.id.box5).setOnClickListener { onBoxClick("Entertainment") }
        findViewById<View>(R.id.box6).setOnClickListener { onBoxClick("Income") }
        findViewById<View>(R.id.box7).setOnClickListener { onBoxClick("Others") }
        mySQLiteHelper = MySQLiteHelper(this)
        val totalIncome = mySQLiteHelper.totalBalance()
        val totalExpenses = mySQLiteHelper.totalExpense()
        val totalBalance = totalIncome - (-totalExpenses)
        // Update UI
        val balanceTextView = findViewById<TextView>(R.id.textViewBalanceAmount)
        val expensesTextView = findViewById<TextView>(R.id.textViewExpensesAmount)

        balanceTextView.text = "$${String.format("%.2f", totalBalance)}"
        expensesTextView.text = "$${String.format("%.2f", totalExpenses)}"
        // Handle Transaction icon click
        val transactionIcon = findViewById<ImageView>(R.id.imageViewIcon3)
        transactionIcon.setOnClickListener {
            val intent = Intent(this, TransactionActivity::class.java)
            startActivity(intent)
        }
        // Categories page
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
        // Analysis page
        val analysisIcon: ImageView = findViewById(R.id.imageViewIcon2)
        analysisIcon.setOnClickListener {
            val intent = Intent(this, AnalysisActivity::class.java)
            startActivity(intent)
        }

        val homeIcon: ImageView = findViewById(R.id.imageViewIcon1)

        // Set an OnClickListener on the ImageView
         homeIcon.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        val backArrow = findViewById<ImageView>(R.id.imageViewBackArrow)
        backArrow.setOnClickListener {
            // Handle back navigation
            onBackPressed()
        }
        updateProgressBar(totalIncome, totalExpenses)
    }
    private fun updateProgressBar(totalIncome: Double, totalExpenses: Double) {
        val percentage = if (totalIncome > 0) (totalExpenses / totalIncome) * 100 else 0.0

        // Adjust this multiplier based on your screen's density and desired progress width
        val density = resources.displayMetrics.density

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // Set the progress as an integer value
        progressBar.progress = density.toInt()
    }
    private fun onBoxClick(category: String) {
        val intent = Intent(this, CategoryItemActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }
}