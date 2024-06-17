package vikas.eu.inviertoapp.viewmodel

import vikas.eu.inviertoapp.entidad.Inversion
import vikas.eu.inviertoapp.entidad.Transaccion

data class InviertoUIState(
    var inversion: Inversion? = null,
    var transaccion: Transaccion? = null,
    var listaInversiones: List<Inversion> = emptyList()

    )
