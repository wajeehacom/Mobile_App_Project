import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.JsonObject
import java.lang.reflect.Type

class TransactionSerializer : JsonSerializer<Transaction> {
    override fun serialize(src: Transaction, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val jsonObject = JsonObject()
        // Order fields as required by the server: title, category, amount, date, message
        jsonObject.addProperty("title", src.title)
        jsonObject.addProperty("category", src.category)
        jsonObject.addProperty("amount", src.amount)
        jsonObject.addProperty("date", src.date)
        jsonObject.addProperty("message", src.message)
        return jsonObject
    }
}
