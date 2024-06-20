package vikas.eu.inviertoapp.entidad

import java.time.LocalDate

data class Cuenta (
    var id: Long? = null,

    var numeroCuenta: String? = null,

    var saldo: Double? = null,

    var fechaCreacion: String? = null,

    var bancoId: Long? = null,

    var movimientos: List<Transaccion> = emptyList()
)
