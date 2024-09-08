package com.example.financepal

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.financepal.ProfileActivity

class TransactionActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var mySQLiteHelper: MySQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction) // Ensure this is the correct layout file
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        listView = findViewById(R.id.listView)
        mySQLiteHelper = MySQLiteHelper(this)

        val totalIncome= mySQLiteHelper.totalBalance()
        val totalExpenses = mySQLiteHelper.totalExpense()
        val totalBalance = totalIncome - (-totalExpenses)
        // Update UI
        val balanceTextView = findViewById<TextView>(R.id.textViewBalanceAmount)
        val expensesTextView = findViewById<TextView>(R.id.textViewExpenseAmount)
        val incomeTextView = findViewById<TextView>(R.id.textViewIncomeAmount)

        balanceTextView.text = "$${String.format("%.2f", totalBalance)}"
        expensesTextView.text = "$${String.format("%.2f", totalExpenses)}"
        incomeTextView.text = "$${String.format("%.2f", totalIncome)}"

        val homeIcon: ImageView = findViewById(R.id.imageViewIcon1)

        // Set an OnClickListener on the ImageView
        homeIcon.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
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
        val linearLayoutIncome: LinearLayout = findViewById(R.id.linearLayoutIncome)
        val linearLayoutExpenses: LinearLayout = findViewById(R.id.linearLayoutExpenses)
        linearLayoutIncome.setOnClickListener {
            val intent = Intent(this, IncomeActivity::class.java)
            startActivity(intent)
        }

        linearLayoutExpenses.setOnClickListener {
            val intent = Intent(this, ExpensesActivity::class.java)
            startActivity(intent)
        }
        val profileIcon: ImageView = findViewById(R.id.imageViewIcon5)

        // Set an OnClickListener on the ImageView
        profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Populate Expenses
        populateExpenses()
        val backArrow = findViewById<ImageView>(R.id.imageViewBackArrow)
        backArrow.setOnClickListener {
            // Handle back navigation
            onBackPressed()
        }
    }

    private fun populateExpenses() {
        // Retrieve expenses from the database
        val expenses = mySQLiteHelper.getAllExpenses()

        // Create an adapter and set it to the ListView
        val adapter = HomeAdapter(this, expenses)
        listView.adapter = adapter
    }
}
