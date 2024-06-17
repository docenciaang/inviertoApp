package vikas.eu.inviertoapp.entidad

import java.time.LocalDate

data class Transaccion(

    var id: Long? = null,

    var monto: Double? = null,

    var fecha: String? = null,

    var detalle: String? = null,

    var origneId: Long? = null,

    var destinoId: String? = null,

    var tipo: TipoTransaccion? = null,

    // extra para calculos
    var saldo: Double? = null
)
