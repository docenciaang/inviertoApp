package vikas.eu.inviertoapp.red

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import vikas.eu.inviertoapp.entidad.Cuenta
import vikas.eu.inviertoapp.entidad.Inversion
import vikas.eu.inviertoapp.entidad.RespuestaId



interface CuentaService {
        @GET("cuentas/{id}")
        suspend fun getCuenta(@Path("id") id: Long): Response<Cuenta>

        @GET("cuentas")
        suspend fun getAll(): Response<List<Cuenta>>

        @POST("cuentas")
        suspend fun insertar(@Body cuenta: Cuenta): Response<RespuestaId>

        @DELETE("cuentas/{id}")
        suspend fun borrar(@Path("id")id:Long): Response<Void>

        @PUT("cuentas/{id}")
        suspend fun actualizar(@Path("id")id: Long, @Body cuenta: Cuenta): Response<Cuenta>

        @GET("cuentas/detalle/{id}")
        suspend fun getDetalles( @Path("id") id: Long): Response<Cuenta>



}