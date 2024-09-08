package com.example.financepal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class AnalysisActivity : AppCompatActivity() {

    private lateinit var semiCircularPieChart: SemiCircularPieChart
    private lateinit var databaseHelper: MySQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val backArrow = findViewById<ImageView>(R.id.imageViewBackArrow)
        backArrow.setOnClickListener {
            // Handle back navigation
            onBackPressed()
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

        semiCircularPieChart = findViewById(R.id.semiCircularPieChart)
        databaseHelper = MySQLiteHelper(this)

        // Retrieve expenses data
        val totalFood = databaseHelper.totalAmountSpentOnFood()
        val totalMedicine = databaseHelper.totalAmountSpentOnMedicine()
        val totalTransport = databaseHelper.totalAmountSpentOnTransport()
        val totalGroceries = databaseHelper.totalAmountSpentOnGroceries()
        val totalEntertainment = databaseHelper.totalAmountSpentOnEntertainment()
        val totalOthers = databaseHelper.totalAmountSpentOnOthers()

        val categoryValues = listOf(
            totalFood.toFloat(),
            totalMedicine.toFloat(),
            totalTransport.toFloat(),
            totalGroceries.toFloat(),
            totalEntertainment.toFloat(),
            totalOthers.toFloat()
        )

        //ContextCompat is a helper class from the Android Support Library (now part of AndroidX) that provides backward-compatible functionality for accessing certain resources and performing actions that may behave differently on older Android versions

        semiCircularPieChart.setData(categoryValues)

        val categoryColors = listOf(
            ContextCompat.getColor(this, R.color.foodColor),
            ContextCompat.getColor(this, R.color.medicineColor),
            ContextCompat.getColor(this, R.color.transportColor),
            ContextCompat.getColor(this, R.color.groceriesColor),
            ContextCompat.getColor(this, R.color.entertainmentColor),
            ContextCompat.getColor(this, R.color.othersColor)
        )
        semiCircularPieChart.setColors(categoryColors)

        // Populate legend
        val legendRow1 = findViewById<LinearLayout>(R.id.legendRow1)
        val legendRow2 = findViewById<LinearLayout>(R.id.legendRow2)
        val categories = listOf("Food", "Medicine", "Transport", "Groceries", "Entertainment", "Others")

        for (i in categories.indices) {
            val legendItem = layoutInflater.inflate(R.layout.legend_item, null)
            val colorSquare = legendItem.findViewById<View>(R.id.colorSquare)
            val categoryLabel = legendItem.findViewById<TextView>(R.id.categoryLabel)

            colorSquare.setBackgroundColor(categoryColors[i])
            categoryLabel.text = categories[i]

            if (i < 3) {
                legendRow1.addView(legendItem)
            } else {
                legendRow2.addView(legendItem)
            }
        }
    }
}
