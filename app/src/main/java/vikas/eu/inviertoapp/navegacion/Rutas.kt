package vikas.eu.inviertoapp.navegacion

import kotlinx.serialization.Serializable

sealed class Rutas(var ruta: String) {
    object HOME : Rutas("HOME")

    object  DETALLE_INVERSION: Rutas("Detalle de inversion")
    object TRANSACCION : Rutas("Transacción")
    object NUEVA_TRANSACCION: Rutas("Nueva transacción")

    object DETALLE_CUENTA: Rutas("Detalle cuenta")

    object NUEVA_CUENTA: Rutas("Nueva cuenta")
}