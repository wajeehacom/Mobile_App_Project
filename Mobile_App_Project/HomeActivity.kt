
package com.example.financepal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var mySQLiteHelper: MySQLiteHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        listView = findViewById(R.id.listViewExpenses)
        mySQLiteHelper = MySQLiteHelper(this)

        // Initialize UserTransactionRepository
        val totalIncome = mySQLiteHelper.totalBalance()
        val totalExpenses = mySQLiteHelper.totalExpense()
        val foodExpenses = mySQLiteHelper.totalAmountSpentOnFood()
        val totalBalance = totalIncome - (-totalExpenses)

        // Update UI
        val balanceTextView = findViewById<TextView>(R.id.textViewBalanceAmount)
        val expensesTextView = findViewById<TextView>(R.id.textViewExpensesAmount)
        val foodTextView = findViewById<TextView>(R.id.textViewFoodAmount)
        val revenueTextView = findViewById<TextView>(R.id.textViewRevenueAmount)

        balanceTextView.text = "$${String.format("%.2f", totalBalance)}"
        expensesTextView.text = "$${String.format("%.2f", totalExpenses)}"
        foodTextView.text = "$${String.format("%.2f", foodExpenses)}"
        revenueTextView.text = "$${String.format("%.2f", totalIncome)}"

        // Handle Account Balance Icon Click
        val accountBalanceIcon = findViewById<ImageView>(R.id.accountbalanceIcon)
        accountBalanceIcon.setOnClickListener {
            val intent = Intent(this, AccountBalanceActivity::class.java)
            startActivity(intent)
        }

        // Handle Notification Icon Click
        val notificationIcon = findViewById<ImageView>(R.id.notificationIcon)
        notificationIcon.setOnClickListener {
            showFloatingMenu(it)
        }

        // Analysis page
        val analysisIcon: ImageView = findViewById(R.id.imageViewIcon2)
        analysisIcon.setOnClickListener {
            val intent = Intent(this, AnalysisActivity::class.java)
            startActivity(intent)
        }
        // Categories page
        val categoryIcon: ImageView = findViewById(R.id.imageViewIcon4)
        categoryIcon.setOnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
        }

        // Handle Transaction icon click
        val transactionIcon = findViewById<ImageView>(R.id.imageViewIcon3)
        transactionIcon.setOnClickListener {
            val intent = Intent(this, TransactionActivity::class.java)
            startActivity(intent)
        }
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

        // Handle Monthly, Weekly, and Daily button clicks
        val btnMonthly = findViewById<Button>(R.id.btnMonthly)
        val btnWeekly = findViewById<Button>(R.id.btnWeekly)
        val btnDaily = findViewById<Button>(R.id.btnDaily)

        btnMonthly.setOnClickListener {
            val expense = mySQLiteHelper.getCurrentMonthExpenses()
            val adapter = HomeAdapter(this, expense)
            listView.adapter = adapter
        }


        btnDaily.setOnClickListener {
            val expenses = mySQLiteHelper.getCurrentDayExpenses()
            val adapter = HomeAdapter(this, expenses)
            listView.adapter = adapter
        }

        btnWeekly.setOnClickListener {
//            Toast.makeText(this, "Weekly button clicked", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, WeeklyHomeActivity::class.java)
//            startActivity(intent)

            val expense = mySQLiteHelper.getCurrentMonthExpenses()
            val adapter = HomeAdapter(this, expense)
            listView.adapter = adapter
        }

        updateProgressBar(totalIncome, totalExpenses)
        // Populate Expenses
        populateExpenses()


    }


    private fun populateExpenses() {
        // Retrieve expenses from the database
        val expenses = mySQLiteHelper.getAllExpenses()

        // Create an adapter and set it to the ListView
        val adapter = HomeAdapter(this, expenses)
        listView.adapter = adapter
    }

    private fun showFloatingMenu(anchorView: View) {
        // Inflate the custom layout
        val inflater = LayoutInflater.from(this)
        val popupView = inflater.inflate(R.layout.floating_menu, null)

        // Create the PopupWindow
        val popupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        // Set a background drawable (optional)
        popupWindow.setBackgroundDrawable(null)

        // Make the PopupWindow focusable
        popupWindow.isFocusable = true

        // Show the PopupWindow
        popupWindow.showAsDropDown(anchorView, 0, 0)

        // Handle item clicks
        popupView.findViewById<TextView>(R.id.menu_item1).setOnClickListener {
            // Handle "Notification 1" click
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.menu_item2).setOnClickListener {
            // Handle "Notification 2" click
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.menu_item3).setOnClickListener {
            // Handle "Notification 3" click
            popupWindow.dismiss()
        }
    }
    private fun updateProgressBar(totalIncome: Double, totalExpenses: Double) {
        val percentage = if (totalIncome > 0) (totalExpenses / totalIncome) * 100 else 0.0

        // Adjust this multiplier based on your screen's density and desired progress width
        val density = resources.displayMetrics.density
        // Get reference to the ProgressBar
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // Set the progress as an integer value

        progressBar.progress = density.toInt()



        // Find the textViewSavings and set the text
        val textViewSavings = findViewById<TextView>(R.id.textViewSavings)
        textViewSavings.text = "Used ${String.format("%.2f", percentage)}% of the Total Balance"
    }

}