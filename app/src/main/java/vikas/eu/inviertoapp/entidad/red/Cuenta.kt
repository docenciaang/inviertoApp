package vikas.eu.inviertoapp.entidad.red

import java.time.LocalDate

data class Cuenta (
    var id: Long? = null,

    var numeroCuenta: String? = null,

    var saldo: Double? = null,

    var fechaCreacion: LocalDate? = null,

    var bancoId: Long? = null
)
