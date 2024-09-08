package com.example.financepal

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Define database version and name
private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "FinancePal.db"

// Define table and column names
private const val TABLE_USER_TRANSACTION_REPORT = "UserTransactionReport"
private const val COLUMN_ID = "id"
private const val COLUMN_TOTAL_INCOME = "totalIncome"
private const val COLUMN_TOTAL_EXPENSE = "totalExpense"

class MySQLiteHelper2(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Create UserTransactionReport table
        val CREATE_USER_TRANSACTION_REPORT_TABLE = """
            CREATE TABLE $TABLE_USER_TRANSACTION_REPORT (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TOTAL_INCOME REAL DEFAULT 0.0,
                $COLUMN_TOTAL_EXPENSE REAL DEFAULT 0.0
            )
        """.trimIndent()
        db.execSQL(CREATE_USER_TRANSACTION_REPORT_TABLE)

        // Initialize with zero values if necessary
        val initialValues = "INSERT INTO $TABLE_USER_TRANSACTION_REPORT ($COLUMN_TOTAL_INCOME, $COLUMN_TOTAL_EXPENSE) VALUES (0.0, 0.0)"
        db.execSQL(initialValues)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER_TRANSACTION_REPORT")

        // Create tables again
        onCreate(db)
    }

    // Methods for manipulating data in UserTransactionReport table

//    fun getTotalIncome(): Double {
//        val db = this.readableDatabase
//        val query = "SELECT $COLUMN_TOTAL_INCOME FROM $TABLE_USER_TRANSACTION_REPORT LIMIT 1"
//        val cursor = db.rawQuery(query, null)
//        val totalIncome = if (cursor.moveToFirst()) cursor.getDouble(0) else 0.0
//        cursor.close()
//        db.close()
//        return totalIncome
//    }
//
//    fun getTotalExpense(): Double {
//        val db = this.readableDatabase
//        val query = "SELECT $COLUMN_TOTAL_EXPENSE FROM $TABLE_USER_TRANSACTION_REPORT LIMIT 1"
//        val cursor = db.rawQuery(query, null)
//        val totalExpense = if (cursor.moveToFirst()) cursor.getDouble(0) else 0.0
//        cursor.close()
//        db.close()
//        return totalExpense
//    }
fun getTotalExpense(): Double {
    val db = this.readableDatabase
    var totalExpense = 0.0

    val cursor = db.query(
        TABLE_USER_TRANSACTION_REPORT,
        arrayOf(COLUMN_TOTAL_EXPENSE),
        null,
        null,
        null,
        null,
        null
    )

    if (cursor.moveToFirst()) {
        totalExpense = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_EXPENSE))
    }

    cursor.close()
    db.close()

    return totalExpense
}

    fun getTotalIncome(): Double {
        val db = this.readableDatabase
        var totalIncome = 0.0

        val cursor = db.query(
            TABLE_USER_TRANSACTION_REPORT,
            arrayOf(COLUMN_TOTAL_INCOME),
            null,
            null,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            totalIncome = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_INCOME))
        }

        cursor.close()
        db.close()

        return totalIncome
    }


    fun updateTotalIncome(newTotalIncome: Double) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TOTAL_INCOME, newTotalIncome)
        }
        db.update(TABLE_USER_TRANSACTION_REPORT, values, null, null)
        db.close()
    }

    fun updateTotalExpense(newTotalExpense: Double) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TOTAL_EXPENSE, newTotalExpense)
        }
        db.update(TABLE_USER_TRANSACTION_REPORT, values, null, null)
        db.close()
    }
}
