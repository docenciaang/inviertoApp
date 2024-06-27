package vikas.eu.inviertoapp.red

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.CookieHandler
import java.util.concurrent.TimeUnit

 //const val BASE_URL="http://192.168.1.3:8080/api/"
const val BASE_URL="http://192.168.159.114:8080/api/"
//const val BASE_URL="http://tr.vikas.eu/api/"



// Configuracion para el manejo de cookies

val cookieManager = CookieManager().apply{
    setCookiePolicy(CookiePolicy.ACCEPT_ALL)
}
val x = CookieHandler.setDefault(cookieManager)


/**
 * Para depurar añadimos un interceptador que regitra en logcat las trasmisiones
 * y recepciones
 */
val logging = HttpLoggingInterceptor().apply {
    // Configura el nivel de registro. Level.BODY te permite ver el cuerpo de las peticiones y respuestas.
    level = HttpLoggingInterceptor.Level.BODY
}

// Construye el cliente OkHttp y añade el interceptor de registro.
// el produccion no utilizamos logging

val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(15, TimeUnit.SECONDS)  // Tiempo de espera de conexión
    .readTimeout(15, TimeUnit.SECONDS)     // Tiempo de espera de lectura
    .writeTimeout(15, TimeUnit.SECONDS)    // Tiempo de espera de escritura
    .addInterceptor(logging)
    .cookieJar(JavaNetCookieJar(cookieManager))
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okHttpClient) // interceptador
    .build()


object ApiService {
    val inversionService: InversionService by lazy{ retrofit.create(InversionService::class.java)}
    val cuentaService: CuentaService by lazy{ retrofit.create(CuentaService::class.java)}

    val transaccionesService: TransaccionService by lazy{ retrofit.create(TransaccionService::class.java)}
}