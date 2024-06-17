package vikas.eu.inviertoapp.red

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import vikas.eu.inviertoapp.entidad.Inversion
import vikas.eu.inviertoapp.entidad.RespuestaId
import vikas.eu.inviertoapp.entidad.Transaccion

interface TransaccionService {
    @GET("transaciones/{id}")
    suspend fun getTransaccion(@Path("id") id: Long): Response<Transaccion>

    @GET("transaciones")
    suspend fun getAll(): Response<List<Transaccion>>

    @POST("transaciones")
    suspend fun insertar(@Body transaccion: Transaccion): Response<RespuestaId>

    @DELETE("transaciones/{id}")
    suspend fun borrar(@Path("id")id:Long): Response<Void>

    @PUT("transaciones/{id}")
    suspend fun actualizar(@Path("id")id: Long, @Body transaccion: Transaccion): Response<Transaccion>

}