package com.example.financepal

import Transaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var dbHelper: MySQLiteHelper
    private lateinit var dbHelper2: UserTransactionReport
    private lateinit var editTextDate: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var editTextAmount: EditText
    private lateinit var editTextTitle: EditText
    private lateinit var editTextMessage: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addexpense)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        dbHelper = MySQLiteHelper(this)
        dbHelper = MySQLiteHelper(this)

        editTextDate = findViewById(R.id.editTextDate)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        editTextAmount = findViewById(R.id.editTextAmount)
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextMessage = findViewById(R.id.editTextMessage)

        val buttonSave: Button = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            addExpense()
        }
        val backArrow = findViewById<ImageView>(R.id.imageViewBackArrow)
        backArrow.setOnClickListener {
            // Handle back navigation
            onBackPressed()
        }
    }
    private fun addExpense() {
        val date = editTextDate.text.toString()
        val category = spinnerCategory.selectedItem.toString()
        val amount = editTextAmount.text.toString().toDoubleOrNull()
        val title = editTextTitle.text.toString()
        val message = editTextMessage.text.toString()

        if (date.isEmpty() || category.isEmpty() || amount == null || title.isEmpty() || message.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val expense = Transaction(title = title, category = category, amount = amount, date = date, message = message)
        dbHelper.insertExpense(expense)


        Toast.makeText(this, "Expense added: Date: $date, Category: $category, Amount: $amount, Title: $title, Message: $message", Toast.LENGTH_LONG).show()
        finish()
    }


//    private fun addExpense() {
//        val date = editTextDate.text.toString()
//        val category = spinnerCategory.selectedItem.toString()
//        val amount = editTextAmount.text.toString().toDoubleOrNull()
//        val title = editTextTitle.text.toString()
//        val message = editTextMessage.text.toString()
//
//        if (date.isEmpty() || category.isEmpty() || amount == null || title.isEmpty() || message.isEmpty()) {
//            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val expense = Expense(title, category, amount, date, message)
//        dbHelper.insertExpense(expense)
//
//        Toast.makeText(this, "Expense added: Date: $date, Category: $category, Amount: $amount, Title: $title, Message: $message", Toast.LENGTH_LONG).show()
//        finish()
//    }

    fun showDatePicker(view: View) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }
                val dateFormat = SimpleDateFormat("d MMMM, yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedCalendar.time)
                editTextDate.setText(formattedDate)
            }, year, month, day)

        datePickerDialog.show()
    }
}