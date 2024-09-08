import com.example.financepal.ApiService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.cert.X509Certificate

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:7139/api/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val unsafeOkHttpClient: OkHttpClient by lazy {
//        val trustAllCertificates = object : X509TrustManager {
//            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
//            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
//            override fun getAcceptedIssuers(): Array<X509Certificate>? = null
//        }
//
//        val sslContext = SSLContext.getInstance("SSL")
//        sslContext.init(null, arrayOf<TrustManager>(trustAllCertificates), java.security.SecureRandom())

        OkHttpClient.Builder()
//            .sslSocketFactory(sslContext.socketFactory, trustAllCertificates)
            .hostnameVerifier { _, _ -> true }
            .addInterceptor(logging)
            .build()
    }

    private val gson = GsonBuilder()
        .setLenient()
        .registerTypeAdapter(Transaction::class.java, TransactionSerializer())
        .create()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(unsafeOkHttpClient) // Use the insecure client
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}
