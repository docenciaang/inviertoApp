package vikas.eu.inviertoapp.ui.pantallas

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import vikas.eu.inviertoapp.entidad.TipoTransaccion
import vikas.eu.inviertoapp.entidad.Transaccion
import vikas.eu.inviertoapp.viewmodel.InvViewModel
import vikas.eu.inviertoapp.viewmodel.TipoOperacion



val transaccionesPermitidasInversion = listOf(
    TipoTransaccion.AJUSTE,
    TipoTransaccion.TRASPASO,
    TipoTransaccion.COMPRA,
    TipoTransaccion.VENTA,
    TipoTransaccion.DIVIDENDO,
    TipoTransaccion.INTERESES,
    TipoTransaccion.REVALORIZACION,
)

/**
 * Crea una Transaccion
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
    var fechaT by remember { mutableStateOf("") }
    // var descripcionT by remember { mutableStateOf("") }

    var transaccion by remember { mutableStateOf(  Transaccion(id = 0, tipo = TipoTransaccion.AJUSTE)) }


    Column {
        Text(text = "TRANSACCIÓN INVERSION")


        ExposedDropdownMenuSample(
            label = "Tipo transaccion",
            options = transaccionesPermitidasInversion,
        ) { elegido ->

            transaccion = transaccion.copy(tipo = elegido)
            Log.d("MiDebug" , "elegido = ${transaccion.tipo}")
        }

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text(text = "Cantidad (€)") })
        OutlinedTextField(
            value = fechaT,
            onValueChange = { fechaT = it },
            label = { Text(text = "fecha (yyyy-mm-dd)") })


        /**
         * Campos comunes : cantidad, fecha y descripcion
         */


      //  if (comprobarCompatibilidad(uis.value.operacion, uis.value.transaccion?.tipo!!))
            when (transaccion.tipo) {
                TipoTransaccion.TRASPASO -> {
                    // inversion destino
                    transaccion.origenId = uis.value.inversion?.id
                    // necesitamos un valor por defecto
                    transaccion.destinoId = uis.value.listaInversiones.get(0).id

                    ElegirCuentaInversion(
                        msg = "Inversion destino",
                        opciones = uis.value.listaInversiones
                    ) {
                        transaccion.destinoId = it.id
                    }
                }

                TipoTransaccion.COMPRA -> {
                    transaccion.origenId = uis.value.listaCuentas.get(0).id
                    ElegirCuentaInversion(
                        msg = "Cuenta de cargo",
                        opciones = uis.value.listaCuentas
                    ) {
                        transaccion.destinoId = uis.value.inversion?.id
                        transaccion.origenId = it.id
                    }

                }

                TipoTransaccion.VENTA -> {
                    // cuenta destino
                    transaccion.destinoId = uis.value.listaCuentas.get(0).id
                    ElegirCuentaInversion(
                        msg = "Cuenta destino",
                        opciones = uis.value.listaCuentas
                    ) {
                        transaccion.destinoId = it.id
                    }
                    transaccion.origenId = uis.value.inversion?.id

                }

                TipoTransaccion.AJUSTE -> {
                    transaccion.apply {
                        origenId = uis.value.inversion!!.id
                        destinoId = uis.value.inversion!!.id
                    }

                }

                TipoTransaccion.DIVIDENDO -> {
                    // cantidad
                    transaccion.destinoId = uis.value.listaCuentas.get(0).id
                    ElegirCuentaInversion(
                        msg = "Cuenta ingreso dividendo",
                        opciones = uis.value.listaCuentas
                    ) {
                        transaccion.origenId = uis.value.inversion!!.id
                        transaccion.destinoId = it.id
                    }
                }

                TipoTransaccion.INTERESES -> {
                    ElegirCuentaInversion(
                        msg = "Cuenta ingreso innrwewa",
                        opciones = uis.value.listaCuentas
                    ) {
                        transaccion.origenId = uis.value.inversion!!.id
                        transaccion.destinoId = it.id
                    }
                }

                TipoTransaccion.REVALORIZACION -> {
                    transaccion.apply {
                        origenId = uis.value.inversion!!.id
                        destinoId = origenId
                    }
                }

                null -> TODO("tipo transaccion nunca nulo")
                else -> TODO("no deberia llegar al else")

            }


        Button(onClick = {
            transaccion.apply {
                monto = cantidad.toDoubleOrNull() ?: 0.0
                fecha = fechaT
            }
            vm.guardarTransaccion(transaccion)
            onSelect()
        }) {
            Text(text = "Crear")
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
 * origen INVERSION, destino inversion
 * Se incrementa las participaciones de la inversion
 * Necesitamos cuenta origen
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ElegirCuentaInversion(
    msg: String,
    opciones: List<T>,
    onSelect: (T) -> Unit
) {
    var opcionSeleccionada by remember {
        mutableStateOf(opciones.get(0))
    }
    //  val optionStrings = listOf("")
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(opciones[0]) }



    Column {
        Text(text = msg)

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {

            TextField(
                modifier = Modifier.menuAnchor(),
                value = text.toString(),
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                label = { Text("Cuenta cargo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                opciones.forEachIndexed { index, cc: T ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                cc.toString(),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        onClick = {
                            opcionSeleccionada = cc
                            onSelect(opcionSeleccionada)
                            expanded = false

                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }

//        Button(onClick = {
//            onSelect(opcionSeleccionada)
//        }) {
//            Text(text = "Ok")
//        }
    }
}