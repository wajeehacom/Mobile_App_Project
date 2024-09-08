package com.example.financepal


import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AccountBalanceActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var mySQLiteHelper: MySQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balancecheck)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        listView = findViewById(R.id.listView) // Ensure this ID matches the one in your XML
        mySQLiteHelper = MySQLiteHelper(this)
        val totalBalance = mySQLiteHelper.totalBalance()
        val totalExpenses = mySQLiteHelper.totalExpense()
        val netBalance = totalBalance - totalExpenses
        // Update UI
        val balanceTextView = findViewById<TextView>(R.id.textViewBalanceAmount)
        val expensesTextView = findViewById<TextView>(R.id.textViewExpensesAmount)

        balanceTextView.text = "$${String.format("%.2f", totalBalance)}"
        expensesTextView.text = "$${String.format("%.2f", totalExpenses)}"
        // Set text for new TextViews
        val incomeBoxAmount = findViewById<TextView>(R.id.incomeboxamount)
        val expenseBoxAmount = findViewById<TextView>(R.id.expenseboxamount)

        incomeBoxAmount.text = "$${String.format("%.2f", totalBalance)}"
        expenseBoxAmount.text = "$${String.format("%.2f", totalExpenses)}"
        val profileIcon: ImageView = findViewById(R.id.imageViewIcon5)

        // Set an OnClickListener on the ImageView
        profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        val homeIcon: ImageView = findViewById(R.id.imageViewIcon1)

        // Set an OnClickListener on the ImageView
        homeIcon.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
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
