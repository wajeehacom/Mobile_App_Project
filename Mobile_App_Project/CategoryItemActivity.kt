package com.example.financepal


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CategoryItemActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var dbHelper: MySQLiteHelper
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoryitem)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        listView = findViewById(R.id.listViewCategoryItems)
        dbHelper = MySQLiteHelper(this) // Initialize your database helper here

        category = intent.getStringExtra("category") ?: ""
        val textViewHeading = findViewById<TextView>(R.id.textViewHeading) // Ensure this ID matches your layout

        // Set the text to the retrieved category
        textViewHeading.text = category

        refreshExpenseList(dbHelper, category)
        val totalIncome = dbHelper.totalBalance()
        val totalExpenses = dbHelper.totalExpense()
        val totalBalance = totalIncome - (-totalExpenses)
        // Update UI
        val balanceTextView = findViewById<TextView>(R.id.textViewBalanceAmount)
        val expensesTextView = findViewById<TextView>(R.id.textViewExpensesAmount)


        balanceTextView.text = "$${String.format("%.2f", totalBalance)}"
        expensesTextView.text = "$${String.format("%.2f", totalExpenses)}"

        updateProgressBar(totalIncome, totalExpenses)
        val addExpenseButton = findViewById<Button>(R.id.buttonAddExpense)
        addExpenseButton.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
        val homeIcon: ImageView = findViewById(R.id.imageViewIcon1)

        // Set an OnClickListener on the ImageView
        homeIcon.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        val profileIcon: ImageView = findViewById(R.id.imageViewIcon5)

        // Set an OnClickListener on the ImageView
        profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        // Categories page
        val categoryIcon: ImageView = findViewById(R.id.imageViewIcon4)
        categoryIcon.setOnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
        }

        // Analysis page
        val analysisIcon: ImageView = findViewById(R.id.imageViewIcon2)
        analysisIcon.setOnClickListener {
            val intent = Intent(this, AnalysisActivity::class.java)
            startActivity(intent)
        }

        // Handle Transaction icon click
        val transactionIcon = findViewById<ImageView>(R.id.imageViewIcon3)
        transactionIcon.setOnClickListener {
            val intent = Intent(this, TransactionActivity::class.java)
            startActivity(intent)
        }
        val backArrow = findViewById<ImageView>(R.id.imageViewBackArrow)
        backArrow.setOnClickListener {
            // Handle back navigation
            onBackPressed()
            }
    }
    private fun updateProgressBar(totalIncome: Double, totalExpenses: Double) {

        val percentage = if (totalIncome > 0) (totalExpenses / totalIncome) * 100 else 0.0

        val density = resources.displayMetrics.density

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        progressBar.progress = density.toInt()

    }

    private fun refreshExpenseList(dbHelper: MySQLiteHelper, category: String) {
        val expenses = dbHelper.getExpensesByCategory(category)
        val adapter = ExpenseAdapter(this, expenses)
        listView.adapter = adapter
        // Show a Toast if no expenses are found
        if (expenses.isEmpty()) {
            Toast.makeText(this, "No expenses to display for category: $category", Toast.LENGTH_SHORT).show()
        }
    }
}



