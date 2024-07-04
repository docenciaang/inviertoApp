package vikas.eu.inviertoapp.ui.pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import vikas.eu.inviertoapp.ui.componentes.FechaPicker
import vikas.eu.inviertoapp.ui.componentes.LineaCabecera
import vikas.eu.inviertoapp.ui.componentes.LineaMovimiento
import vikas.eu.inviertoapp.viewmodel.InvViewModel

/**
 * Detalle de una cuenta.
 * Modificación y creación
 * @param vm ViewModel
 * @param onTransaccion Callback para añadir nueva transaccion a la cuenta
 * @param onGuardar Callback para la guardar o cancelar la modificación
 */
@Composable
fun PDetalleCuenta(
    vm: InvViewModel = viewModel(),
    onTransaccion: (nueva: Boolean) -> Unit,
    onGuardar: (seGuarda: Boolean) -> Unit
) {
    val uis = vm.uis.collectAsState()
    var numeroCuenta by remember { mutableStateOf(uis.value.cuenta?.numeroCuenta ?: "") }
    var saldo by remember { mutableStateOf(uis.value.cuenta?.saldo ?: 0.0) }
    var fechaCreacion by remember { mutableStateOf(uis.value.cuenta?.fechaCreacion ?: "") }

    var contador by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = Unit) {
        if(uis.value.cuenta?.id != 0L) {
            vm.detalleCuenta()
        }

    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {

        if (uis.value.cuenta?.id == null)
            Text("NUEVA CUENTA")


        OutlinedTextField(
            value = numeroCuenta,
            onValueChange = { numeroCuenta = it },
            label = { Text(text = "Num. Cuenta") })
        OutlinedTextField(
            value = "${uis.value.cuenta?.saldo}" ,
            readOnly = true,
            onValueChange = {},
            label = { Text(text = "Saldo") }
        )
        FechaPicker(label = "Fecha creación") {
            fechaCreacion = it.toString()
        }
//        OutlinedTextField(
//            value = fechaCreacion,
//            onValueChange = { fechaCreacion = it },
//            label = { Text(text = "Fecha creación") }
//        )
        HorizontalDivider()
        Text(text = "Movimientos")
        LineaCabecera()
        LazyColumn(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uis.value.cuenta?.let {
                items(it.movimientos) { tra ->
                    Box(
                        modifier = Modifier.clickable {
                            vm.setTransaccion(tra)
                            contador++
                            onTransaccion(false)
                        }
                    ) {

                        LineaMovimiento(trans = tra)
                    }

                }
                item {
                    FloatingActionButton(onClick = {
                        contador++
                        onTransaccion(true)
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            }

        }


        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = {
                val cuenta = uis.value.cuenta?.copy(
                    numeroCuenta = numeroCuenta,
                    fechaCreacion = fechaCreacion,
                    saldo = saldo
                )
                vm.crearOActualizarCuenta(cuenta!!)
                onGuardar(true)
            }) {
                Text(text = "Guardar")
            }
            TextButton(onClick = {
                onGuardar(false)
            }) {
                Text(text = "Cancelar")
            }
        }


    }

}



