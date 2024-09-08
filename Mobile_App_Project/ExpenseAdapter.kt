package com.example.financepal

import Transaction
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ExpenseAdapter(context: Context, private val expenses: List<Transaction>) :
    ArrayAdapter<Transaction>(context, 0, expenses) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val expense = getItem(position) ?: return convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_expense, parent, false)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_expense, parent, false)

        val titleTextView = view.findViewById<TextView>(R.id.textViewTitle)
        val amountTextView = view.findViewById<TextView>(R.id.textViewAmount)
        val iconImageView = view.findViewById<ImageView>(R.id.imageViewCategoryIcon)
        val date = view.findViewById<TextView>(R.id.textViewDate)

        titleTextView.text = expense.title
        date.text = expense.date

        // Set icon based on category
        when (expense.category) {
            "Food" -> iconImageView.setImageResource(R.drawable.dinner)
            "Transport" -> iconImageView.setImageResource(R.drawable.transport)
            "Groceries" -> iconImageView.setImageResource(R.drawable.groceries_icon)
            "Medicine" -> iconImageView.setImageResource(R.mipmap.medicine)
            "Entertainment" -> iconImageView.setImageResource(R.drawable.shopping_icon)
            "Income" -> iconImageView.setImageResource(R.drawable.salary)
            else -> iconImageView.setImageResource(R.drawable.more)
        }

        // Check if the expense is an income or an expense and set color and sign accordingly
        if (expense.category == "Income") {
            amountTextView.setTextColor(Color.BLACK)  // Set text color for income
            amountTextView.text = "+${expense.amount}"  // Add "+" sign for income
        } else {
            amountTextView.setTextColor(Color.BLUE)  // Set text color for expenses
            amountTextView.text = "-${expense.amount}"  // Add "-" sign for expenses
        }

        return view
    }
}




