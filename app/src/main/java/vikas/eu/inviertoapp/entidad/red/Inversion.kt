package vikas.eu.inviertoapp.entidad.red

import eu.vikas.invierto.model.TipoInversion
import java.time.LocalDate

data class Inversion(
    val id: Long,
    var monto: Double? = null,
    var fechaInversion: LocalDate? = null,
    var fechaVencimiento: LocalDate? = null,
    var tasaInteres: Double? = null,
    var nombreFondo: String? = null,
    var valorActual: Double? = null,
    var tipo: TipoInversion? = null,
    var bancoId: Long? = null
)
