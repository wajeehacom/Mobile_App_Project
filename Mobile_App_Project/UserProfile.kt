
data class UserProfile(
    val id: Int,
    val userName: String,
    val password: String,
    val phone: String = "",  // Optional with a default value
    val email: String,
    val imagePath: String? = null  // Optional with a default value
)
