package vikas.eu.inviertoapp.ui.pantallas

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
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
import vikas.eu.inviertoapp.ui.componentes.FechaPicker
import vikas.eu.inviertoapp.viewmodel.InvViewModel
import vikas.eu.inviertoapp.viewmodel.TipoOperacion
import java.time.LocalDateTime

val listaTransaccionCuenta = listOf(
     TipoTransaccion.AJUSTE,
     TipoTransaccion.TRASPASO,
     TipoTransaccion.SALIDA,
     TipoTransaccion.ENTRADA
 )

/**
 * Crea una Transaccion
 * Si es una nueva transacción el id=0 pero
 * debe tener el origen y/o destino, que debe coincidir con la inversion o cuenta en uistate
 * para poder ser reutilizable en varioas ventanas
 */
@Composable
fun PTransaccionCuenta(
    vm: InvViewModel = viewModel(),
    onSelect: () -> Unit
) {
    val uis = vm.uis.collectAsState()
    var cantidad by remember { mutableStateOf(if(uis.value.transaccion?.id == 0L) "" else uis.value.transaccion?.monto.toString()) }
    var fechaT by remember { mutableStateOf(if(uis.value.transaccion?.id == 0L) "" else uis.value.transaccion?.fecha) }
    var msg by remember { mutableStateOf("") }
    var hayError by remember { mutableStateOf(false) }

    var transaccion by remember { mutableStateOf(  Transaccion(id = 0, tipo = TipoTransaccion.AJUSTE)) }


    Column(
        modifier = Modifier.fillMaxWidth(0.9f),

    ) {
        Text(text = "TRANSACCIÓN CUENTA (${uis.value.cuenta?.saldo} €)")

        ExposedDropdownMenuSample(
            label = "Tipo transaccion",
            options =  listaTransaccionCuenta, // TipoTransaccion.values().toList(),
        ) { elegido ->

            transaccion = transaccion.copy(tipo = elegido)
           // Log.d("MiDebug" , "elegido = ${transaccion.tipo}")
        }

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text(text = "Cantidad (€)") })

        FechaPicker(
            label = "Fecha",
            fechaMinima = LocalDateTime.of(1990,1,1,0,0)
            ) {
            fechaT = it.toString()
        }


        /**
         * Campos comunes : cantidad, fecha y descripcion
         */


      //  if (comprobarCompatibilidad(uis.value.operacion, uis.value.transaccion?.tipo!!))
            when (transaccion.tipo) {
                TipoTransaccion.AJUSTE -> {
                    transaccion.apply {
                        origenId = uis.value.cuenta!!.id
                        destinoId = uis.value.cuenta!!.id
                    }

                }
                TipoTransaccion.TRASPASO -> {
                    // inversion destino
                    transaccion.origenId = uis.value.cuenta?.id
                    // necesitamos un valor por defecto
                    transaccion.destinoId = uis.value.listaCuentas.get(0).id

                    ElegirCuentaInversion2(
                        msg = "Inversion destino",
                        opciones = uis.value.listaCuentas
                    ) {
                        transaccion.destinoId = it.id
                    }
                }

                TipoTransaccion.SALIDA -> {
                    transaccion.origenId = uis.value.cuenta?.id
                    transaccion.destinoId = null

                }
                TipoTransaccion.ENTRADA -> {
                    transaccion.origenId = null
                    transaccion.destinoId = uis.value.cuenta?.id
                }

                null -> TODO("tipo transaccion nunca nulo")
                else -> TODO("OPERACION NO PERMITIDA PARA CC: ${transaccion.tipo}")

            }


        Button(onClick = {
            transaccion.apply {
                monto = cantidad.toDoubleOrNull() ?: 0.0

                if(fechaT ==""){
                    hayError = true
                    msg = "La fecha es obligatoria"
                }else
                    fecha = fechaT

                // comprobaciones
                when(transaccion.tipo){
                    TipoTransaccion.TRASPASO -> {
                        if (monto!! > uis.value.cuenta?.saldo!!){
                            hayError= true
                            msg="No hay saldo suficiente en ${uis.value.cuenta.toString()}"
                        }
                    }
          
                    TipoTransaccion.AJUSTE -> {
                        //nada
                    }
                    TipoTransaccion.ENTRADA -> {
                        //nada
                    }
                    TipoTransaccion.SALIDA -> {
                        if (monto!! > uis.value.cuenta?.saldo!!){
                            hayError= true
                            msg="No hay saldo suficiente en ${uis.value.cuenta.toString()}"
                        }
                    }
                    null -> TODO()
                  else -> TODO("ERROR TIPO TRANSACCION CC")

                }
              
            }
            if ( ! hayError){
                vm.guardarTransaccion(transaccion)
                onSelect()
            }
        }) {
            Text(text = "Crear")
        }
    }

    if (  hayError){
        AlertDialog(
            onDismissRequest = { hayError= false  },
            confirmButton = {
                Button(onClick = { hayError= false }) {
                    Text(text = "OK")
                }
                 },
            title = { Text(text = "Error entrada")},
            text = { Text(text = msg)}
            )
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
fun <T> ElegirCuentaInversion2(
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


    }
}