package vikas.eu.inviertoapp.viewmodel

import vikas.eu.inviertoapp.entidad.Banco
import vikas.eu.inviertoapp.entidad.Cuenta
import vikas.eu.inviertoapp.entidad.Inversion
import vikas.eu.inviertoapp.entidad.Transaccion

data class InviertoUIState(
    val inversion: Inversion? = null,
    val cuenta: Cuenta? = null,
    val transaccion: Transaccion? = null,
    val listaInversiones: List<Inversion> = emptyList(),
    val listaCuentas: List<Cuenta> = emptyList(),
    val listaBancos: List<Banco> = emptyList(),

    val operacion: TipoOperacion = TipoOperacion.NULA

    )

enum class TipoOperacion{
    NULA,
    OPERACION_CUENTA,
    OPERACION_INVERSION
}