package vikas.eu.inviertoapp.entidad

import java.time.LocalDate

data class Inversion(
    val id: Long,
    var monto: Double? = null,
    var fechaInversion: String? = null,
    var fechaVencimiento: String? = null,
    var tasaInteres: Double? = null,
    var nombreFondo: String? = null,
    var valorActual: Double? = null,
    var tipo: TipoInversion? = null,
    var bancoId: Long? = null,
    var archivado: Int? = null,
    var movimientos: List<Transaccion> = emptyList()

)
