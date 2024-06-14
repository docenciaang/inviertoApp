package vikas.eu.inviertoapp.entidad.red

import eu.vikas.invierto.model.TipoTransaccion
import java.time.LocalDate

data class Transaccion(

    var id: Long? = null,

    var monto: Double? = null,

    var fecha: LocalDate? = null,

    var detalle: String? = null,

    var origneId: Long? = null,

    var destinoId: String? = null,

    var tipo: TipoTransaccion? = null
)
