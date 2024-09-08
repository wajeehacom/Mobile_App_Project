package com.example.financepal

import Transaction
import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MySQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ExpensesDatabase"
        private const val DATABASE_VERSION = 6
        const val TABLE_USER_TRANSACTION_REPORT = "UserTransactionReport"
        const val COLUMN_TOTAL_INCOME = "totalIncome"
        const val COLUMN_TOTAL_EXPENSE = "totalExpense"
        const val TABLE_EXPENSES = "expenses"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_DATE = "date"
        const val COLUMN_MESSAGE = "message"


    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_EXPENSES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TITLE TEXT," +
                "$COLUMN_CATEGORY TEXT," +
                "$COLUMN_AMOUNT REAL," +
                "$COLUMN_DATE TEXT," +
                "$COLUMN_MESSAGE TEXT)")
        db.execSQL(createTable)
        // Create the UserTransactionReport table
        val createUserTransactionReportTable = """
        CREATE TABLE UserTransactionReport (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            totalIncome REAL DEFAULT 0,
            totalExpense REAL DEFAULT 0
        )
    """
        db.execSQL(createUserTransactionReportTable)

        // Optionally, insert an initial row into UserTransactionReport table if necessary
        val insertInitialReport = """
        INSERT INTO UserTransactionReport (totalIncome, totalExpense) VALUES (0, 0)
    """
        db.execSQL(insertInitialReport)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EXPENSES")
        onCreate(db)
    }
    fun deleteAllExpenses() {
        val db = this.writableDatabase
        val query = "DELETE FROM $TABLE_EXPENSES"
        db.execSQL(query)
        db.close()
    }

    fun insertExpense(expense: Transaction) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, expense.title)
            put(COLUMN_CATEGORY, expense.category)
            put(COLUMN_AMOUNT, expense.amount)
            put(COLUMN_DATE, expense.date)
            put(COLUMN_MESSAGE, expense.message)
        }
        db.insert(TABLE_EXPENSES, null, values)
        db.close()
    }

//
//    fun insertExpense(expense: Transaction) {
//        val db = this.writableDatabase
//        val values = ContentValues().apply {
//            put(COLUMN_TITLE, expense.title)
//            put(COLUMN_CATEGORY, expense.category)
//            put(COLUMN_AMOUNT, expense.amount)
//            put(COLUMN_DATE, expense.date)
//            put(COLUMN_MESSAGE, expense.message)
//        }
//        db.insert(TABLE_EXPENSES, null, values)
//
//        // Check if the category is "income" or any other category
//        val updateColumn = if (expense.category.equals("income", ignoreCase = true)) {
//            "totalIncome"
//        } else {
//            "totalExpense"
//        }
//
//        // Retrieve the current total value from UserTransactionReport
//        val cursor = db.rawQuery("SELECT $updateColumn FROM UserTransactionReport", null)
//        if (cursor.moveToFirst()) {
//            val currentTotal = cursor.getDouble(0)
//
//            // Update the total in UserTransactionReport
//            val updatedTotal = currentTotal + expense.amount
//            val updateValues = ContentValues().apply {
//                put(updateColumn, updatedTotal)
//            }
//            db.update("UserTransactionReport", updateValues, null, null)
//        }
//        cursor.close()
//        db.close()
//    }
//




    fun getIncomeExpenses(): List<Transaction> {
        val expenses = mutableListOf<Transaction>()
        val db = this.readableDatabase
        val selection = "$COLUMN_CATEGORY COLLATE NOCASE = ?"
        val selectionArgs = arrayOf("Income")
        val cursor = db.query(TABLE_EXPENSES, null, selection, selectionArgs, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val message = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MESSAGE))

                val expense = Transaction(title, "Income", amount, date, message)  // Ensure category is set to "Income"
                expenses.add(expense)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return expenses
    }

    fun getExpensesExcludingIncome(): List<Transaction> {
        val expenses = mutableListOf<Transaction>()
        val db = this.readableDatabase
        val selection = "$COLUMN_CATEGORY COLLATE NOCASE != ?"
        val selectionArgs = arrayOf("Income")
        val cursor = db.query(TABLE_EXPENSES, null, selection, selectionArgs, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val message = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MESSAGE))
                val expense = Transaction(title, category, amount, date, message)
                expenses.add(expense)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return expenses
    }

    fun getAllExpenses(): List<Transaction> {
        val expenses = mutableListOf<Transaction>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_EXPENSES, null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val message = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MESSAGE))
                val expense = Transaction(title, category, amount, date, message)
                expenses.add(expense)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return expenses
    }
    fun getExpensesByCategory(category: String): List<Transaction> {
        val expenses = mutableListOf<Transaction>()
        val db = this.readableDatabase
        val selection = "$COLUMN_CATEGORY COLLATE NOCASE = ?" // Case-insensitive comparison
        val selectionArgs = arrayOf(category)
        val cursor = db.query(TABLE_EXPENSES, null, selection, selectionArgs, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val message = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MESSAGE))
                val expense = Transaction(title, category, amount, date, message)
                expenses.add(expense)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return expenses
    }

//    fun totalBalance(): Double {
//        val db = this.readableDatabase
//        val query = "SELECT totalIncome FROM UserTransactionReport LIMIT 1"
//        val cursor = db.rawQuery(query, null)
//        var totalIncome = 0.0
//
//        if (cursor.moveToFirst()) {
//            totalIncome = cursor.getDouble(cursor.getColumnIndexOrThrow("totalIncome"))
//        }
//
//        cursor.close()
//        db.close()
//        return totalIncome
//    }
//
//    fun totalExpense(): Double {
//        val db = this.readableDatabase
//        val query = "SELECT totalExpense FROM UserTransactionReport LIMIT 1"
//        val cursor = db.rawQuery(query, null)
//        var totalExpense = 0.0
//
//        if (cursor.moveToFirst()) {
//            totalExpense = cursor.getDouble(cursor.getColumnIndexOrThrow("totalExpense"))
//        }
//
//        cursor.close()
//        db.close()
//        return totalExpense
//    }

        // Method to calculate the total balance from income category
    fun totalBalance(): Double {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_EXPENSES WHERE $COLUMN_CATEGORY = ?", arrayOf("Income"))
        var totalBalance = 0.0
        if (cursor.moveToFirst()) {
            totalBalance = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return totalBalance
    }
    // Method to calculate total expenses excluding income category and subtract it from total balance
    fun totalExpense(): Double {
        val totalBalance = totalBalance()
        val db = this.readableDatabase
        val query = "SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_EXPENSES WHERE $COLUMN_CATEGORY != ?"
        val cursor = db.rawQuery(query, arrayOf("income"))

        var totalExpenses = 0.0
        if (cursor.moveToFirst()) {
            totalExpenses = cursor.getDouble(0) ?: 0.0
        }

        cursor.close()
        db.close()
        return totalBalance - totalExpenses
    }
    fun getMonthlyExpenses(): Map<String, List<Transaction>> {
        val expensesByMonth = mutableMapOf<String, MutableList<Transaction>>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM expenses", null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                val category = cursor.getString(cursor.getColumnIndexOrThrow("category"))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"))
                val date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                val message = cursor.getString(cursor.getColumnIndexOrThrow("message"))

                val month = getMonthFromDate(date)
                val expense = Transaction(title, category, amount, date, message)

                if (expensesByMonth[month] == null) {
                    expensesByMonth[month] = mutableListOf()
                }
                expensesByMonth[month]?.add(expense)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return expensesByMonth
    }
    fun getCurrentMonthExpenses(): List<Transaction> {
        val expenses = mutableListOf<Transaction>()
        val db = this.readableDatabase

        // Get the current month and year
        val calendar = java.util.Calendar.getInstance()
        val currentYear = calendar.get(java.util.Calendar.YEAR)
        val currentMonth = calendar.get(java.util.Calendar.MONTH) // Calendar.MONTH is zero-based, 0 = January

        // Convert zero-based month to one-based month for display
        val monthNames = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        val monthName = monthNames[currentMonth]

        // Construct the query to get expenses for the current month and year
        val query = """
        SELECT * FROM $TABLE_EXPENSES 
        WHERE $COLUMN_DATE LIKE ? 
        AND $COLUMN_DATE LIKE ?
    """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf("%$monthName%", "%$currentYear%"))

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val message = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MESSAGE))
                val expense = Transaction(title, category, amount, date, message)
                expenses.add(expense)
            } while (cursor.moveToNext())
        } else {
            Log.d("Expenses", "No expenses found for the current month.")
        }

        cursor.close()  // Close the cursor
        db.close()      // Close the database connection

        Log.d("Expenses", "Number of expenses: ${expenses.size}")  // Log the number of expenses found
        return expenses
    }

    fun getCurrentDayExpenses(): List<Transaction> {
        val expenses = mutableListOf<Transaction>()
        val db = this.readableDatabase

        // Get the current date components
        val calendar = java.util.Calendar.getInstance()
        val currentYear = calendar.get(java.util.Calendar.YEAR)
        val currentMonthIndex = calendar.get(java.util.Calendar.MONTH)  // Zero-based index
        val currentDay = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        // Convert zero-based month index to month name
        val monthNames = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        val monthName = monthNames[currentMonthIndex]

        // Construct the date string in the format "dd Month, yyyy"
        val currentDate = String.format("%02d %s, %d", currentDay, monthName, currentYear)

        // Construct the SQL query to get expenses for the current day
        val query = """
        SELECT * FROM $TABLE_EXPENSES 
        WHERE $COLUMN_DATE = ?
    """.trimIndent()

        // Execute the query with the current date as a parameter
        val cursor = db.rawQuery(query, arrayOf(currentDate))

        // Process the results
        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val message = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MESSAGE))
                val expense = Transaction(title, category, amount, date, message)
                expenses.add(expense)
            } while (cursor.moveToNext())
        } else {
            Log.d("Expenses", "No expenses found for the current day.")
        }

        cursor.close()  // Close the cursor
        db.close()      // Close the database connection

        Log.d("Expenses", "Number of expenses: ${expenses.size}")  // Log the number of expenses found
        return expenses
    }
    fun getCurrentWeekExpenses(): List<Transaction> {
        val expenses = mutableListOf<Transaction>()
        val db = this.readableDatabase

        // Get the current date
        val calendar = java.util.Calendar.getInstance()
        val currentYear = calendar.get(java.util.Calendar.YEAR)
        val currentWeek = calendar.get(java.util.Calendar.WEEK_OF_YEAR)

        // Set calendar to the start of the week (Sunday)
        calendar.set(java.util.Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        val startOfWeek = calendar.time

        // Set calendar to the end of the week (Saturday)
        calendar.add(java.util.Calendar.WEEK_OF_YEAR, 1)
        calendar.add(java.util.Calendar.DAY_OF_YEAR, -1)
        val endOfWeek = calendar.time

        // Convert dates to the format used in the database
        val dateFormat = java.text.SimpleDateFormat("dd MMMM, yyyy", java.util.Locale.getDefault())
        val startDateStr = dateFormat.format(startOfWeek)
        val endDateStr = dateFormat.format(endOfWeek)

        // Construct the query to get expenses for the current week
        val query = "SELECT * FROM $TABLE_EXPENSES WHERE $COLUMN_DATE BETWEEN ? AND ?"
        val cursor = db.rawQuery(query, arrayOf(startDateStr, endDateStr))

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                val message = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MESSAGE))
                val expense = Transaction(title, category, amount, date, message)
                expenses.add(expense)
            } while (cursor.moveToNext())
        } else {
            Log.d("Expenses", "No expenses found for the current week.")
        }

        cursor.close()  // Close the cursor
        db.close()      // Close the database connection

        Log.d("Expenses", "Number of expenses: ${expenses.size}")  // Log the number of expenses found
        return expenses
    }

    private fun getMonthFromDate(date: String): String {
        // Assuming date is in the format "YYYY-MM-DD"
        val monthNumber = date.substring(5, 7).toInt()
        return when (monthNumber) {
            1 -> "January"
            2 -> "February"
            3 -> "March"
            4 -> "April"
            5 -> "May"
            6 -> "June"
            7 -> "July"
            8 -> "August"
            9 -> "September"
            10 -> "October"
            11 -> "November"
            12 -> "December"
            else -> "Unknown"
        }
    }


//    // Method to calculate the total balance from income category
//    fun totalAmountSpentOnFood(): Double {
//        val db = this.readableDatabase
//        val cursor = db.rawQuery("SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_EXPENSES WHERE $COLUMN_CATEGORY = ?", arrayOf("Food"))
//        var totalBalance = 0.0
//        if (cursor.moveToFirst()) {
//            totalBalance = cursor.getDouble(0)
//        }
//        cursor.close()
//        db.close()
//        return totalBalance
//    }


    // Method to calculate the total balance from income category
    fun totalAmountSpentOnFood(): Double {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_EXPENSES WHERE $COLUMN_CATEGORY = ?", arrayOf("Food"))
        var totalBalance = 0.0
        if (cursor.moveToFirst()) {
            totalBalance = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return totalBalance
    }
    // Method to calculate the total balance from income category
    fun totalAmountSpentOnMedicine(): Double {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_EXPENSES WHERE $COLUMN_CATEGORY = ?", arrayOf("Medicine"))
        var totalBalance = 0.0
        if (cursor.moveToFirst()) {
            totalBalance = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return totalBalance
    }
    // Method to calculate the total balance from income category
    fun totalAmountSpentOnTransport(): Double {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_EXPENSES WHERE $COLUMN_CATEGORY = ?", arrayOf("Transport"))
        var totalBalance = 0.0
        if (cursor.moveToFirst()) {
            totalBalance = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return totalBalance
    }
    // Method to calculate the total balance from income category
    fun totalAmountSpentOnGroceries(): Double {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_EXPENSES WHERE $COLUMN_CATEGORY = ?", arrayOf("Groceries"))
        var totalBalance = 0.0
        if (cursor.moveToFirst()) {
            totalBalance = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return totalBalance
    }
    // Method to calculate the total balance from income category
    fun totalAmountSpentOnEntertainment(): Double {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_EXPENSES WHERE $COLUMN_CATEGORY = ?", arrayOf("Entertainment"))
        var totalBalance = 0.0
        if (cursor.moveToFirst()) {
            totalBalance = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return totalBalance
    }
    // Method to calculate the total balance from income category
    fun totalAmountSpentOnOthers(): Double {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_EXPENSES WHERE $COLUMN_CATEGORY = ?", arrayOf("Others"))
        var totalBalance = 0.0
        if (cursor.moveToFirst()) {
            totalBalance = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return totalBalance
    }

}