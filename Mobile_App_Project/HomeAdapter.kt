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

class HomeAdapter(context: Context, private val expenses: List<Transaction>) :
    ArrayAdapter<Transaction>(context, 0, expenses) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get the current expense item
        val expense = getItem(position) ?: return convertView ?: LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)

        // Inflate the view if it is null
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)

        // Find the views
        val iconImageView = view.findViewById<ImageView>(R.id.imageViewCategoryIcon)
        val nameTextView = view.findViewById<TextView>(R.id.textViewCategoryName)
        val typeTextView = view.findViewById<TextView>(R.id.textViewCategoryType)
        val amountTextView = view.findViewById<TextView>(R.id.textViewAmount)

        // Set the data
        nameTextView.text = expense.title
        typeTextView.text = expense.category // Change to display the category or type if needed
        amountTextView.text = "$${expense.amount}"

        // Set icon based on category
        when (expense.category) {
            "Food" -> iconImageView.setImageResource(R.drawable.dinner)
            "Transport" -> iconImageView.setImageResource(R.drawable.transport)
            "Groceries" -> iconImageView.setImageResource(R.drawable.groceries_icon)
            "Medicine" -> iconImageView.setImageResource(R.mipmap.enter)
            "Shopping" -> iconImageView.setImageResource(R.mipmap.enter)
            "Income" -> iconImageView.setImageResource(R.drawable.salary_icon)
            // Add more cases for different categories
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
