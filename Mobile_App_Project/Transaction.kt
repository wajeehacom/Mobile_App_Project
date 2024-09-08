import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("title")
    val title: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("date")
    val date: String,
    @SerializedName("message")
    val message: String,

)
