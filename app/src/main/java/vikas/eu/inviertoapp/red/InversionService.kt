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



interface InversionService {
        @GET("inversions/{id}")
        suspend fun getInversion(@Path("id") id: Long): Response<Inversion>

        @GET("inversions")
        suspend fun getAll(): Response<List<Inversion>>

        @POST("inversions")
        suspend fun insertar(@Body inversion: Inversion): Response<RespuestaId>

        @DELETE("inversions/{id}")
        suspend fun borrar(@Path("id")id:Long): Response<Void>

        @PUT("inversions/{id}")
        suspend fun actualizar(@Path("id")id: Long, @Body inversion: Inversion): Response<Inversion>

        @GET("inversions/detalle/{id}")
        suspend fun getDetalles( @Path("id") id: Long): Response<Inversion>



}