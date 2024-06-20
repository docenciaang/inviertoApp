package vikas.eu.inviertoapp.ui.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import vikas.eu.inviertoapp.entidad.TipoTransaccion
import vikas.eu.inviertoapp.entidad.Transaccion
import vikas.eu.inviertoapp.viewmodel.InvViewModel
import vikas.eu.inviertoapp.viewmodel.TipoOperacion
import java.time.LocalDate


/**
 * Crea o edita una Transaccion
 * Si es una nueva transacción el id=0 pero
 * debe tener el origen y/o destino, que debe coincidir con la inversion o cuenta en uistate
 * para poder ser reutilizable en varioas ventanas
 */
@Composable
fun PTransaccion(
    vm: InvViewModel = viewModel(),
    onSelect: () -> Unit
) {
    val uis = vm.uis.collectAsState()
    var cantidad by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }


    Column {
        Text(text = "TRANSACCIÓN")

        if (uis.value.operacion == TipoOperacion.OPERACION_CUENTA) {
            Text(text = "OPERACION CON CC : ${uis.value.cuenta?.numeroCuenta}")
        } else if (uis.value.operacion == TipoOperacion.OPERACION_INVERSION) {
            Text(text = "OPERACION CON INVERSION : : ${uis.value.inversion?.nombreFondo}")
        }

        Text(text = " ${uis.value.transaccion?.tipo ?: "--"}")
        if (uis.value.transaccion != null) {
            val trans by remember { mutableStateOf(uis.value.transaccion!!) }

            if (comprobarCompatibilidad(uis.value.operacion, uis.value.transaccion?.tipo!!))
                when (uis.value.transaccion!!.tipo) {
                    TipoTransaccion.TRASPASO -> TODO()
                    TipoTransaccion.COMPRA -> TODO()
                    TipoTransaccion.VENTA -> TODO()
                    TipoTransaccion.AJUSTE -> {
                        Ajuste(
                            cantidad,
                            { cantidad = it },
                            descripcion,
                            { descripcion = it }
                        )
                        Button(onClick = {
                            trans.apply {
                                tipo = TipoTransaccion.AJUSTE
                                monto = cantidad.toDoubleOrNull() ?: 0.0
                                fecha = LocalDate.now().toString()
                                origenId = uis.value.inversion!!.id
                                destinoId = uis.value.inversion!!.id
                                descripcion = descripcion
                            }
                            vm.guardarTransaccion(trans)
                            onSelect()
                        }) {
                            Text(text = "Aceptar")
                        }
                    }

                    TipoTransaccion.DIVIDENDO -> TODO()
                    TipoTransaccion.INTERESES -> TODO()
                    TipoTransaccion.REVALORIZACION -> TODO()

                    // else -> TODO("no deberia llegar al else")
                    TipoTransaccion.ENTRADA -> {

                    }

                    TipoTransaccion.SALIDA -> TODO()
                    null -> TODO("tipo transaccion nunca nulo")
                }
        }

    }
}

/**
 *
 * @param operacion:  cuenta o inversion
 * @param transaccion usamos el tipo
 */
fun comprobarCompatibilidad(operacion: TipoOperacion, transaccion: TipoTransaccion): Boolean {

    if (operacion == TipoOperacion.OPERACION_CUENTA) {
        if (transaccion == TipoTransaccion.REVALORIZACION)
            return false
        else
            return true
    } else {
        if (transaccion in listOf(
                TipoTransaccion.ENTRADA,
                TipoTransaccion.SALIDA,
            )
        )
            return false
        else
            return true
    }
}

/**
 *  Nuevo saldo para corregir errores en el saldo
 */
@Composable
fun Ajuste(
    cantidad: String,
    onCantidadChange: (String) -> Unit,
    descripcion: String,
    onDescripcionChange: (String) -> Unit
) {

    Column {
        TextField(
            onValueChange = onCantidadChange,
            value = cantidad,
            label = { Text("cantidad (€)") }
        )
        TextField(
            onValueChange = onDescripcionChange,
            value = descripcion,
            label = { Text("descripcion") }
        )

    }

}


/**
 * origen cuenta, destino inversion
 *
 */
@Composable
fun Compra(

) {
    Column {
        Text(text = "Compra nuevas participaciones")
    }
}