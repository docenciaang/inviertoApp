package vikas.eu.inviertoapp.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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
    onTransaccion: ( nueva: Boolean) -> Unit,
    onGuardar: (seGuarda: Boolean) -> Unit
) {
    val uis = vm.uis.collectAsState()
    var numeroCuenta by remember { mutableStateOf(uis.value.cuenta?.numeroCuenta ?: "--") }
    var saldo by remember { mutableStateOf(uis.value.cuenta?.saldo ?:0.0) }
    var fechaCreacion by remember { mutableStateOf(uis.value.cuenta?.fechaCreacion ?: "") }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {

        if( uis.value.cuenta?.id == null)
            Text("NUEVA CUENTA")


        OutlinedTextField(
            value = numeroCuenta,
            onValueChange = {numeroCuenta = it},
            label = { Text(text = "Num. Cuenta")})
        OutlinedTextField(
            value = saldo.toString(),
            onValueChange ={saldo = it.toDoubleOrNull() ?: 0.0},
            )

        OutlinedTextField(value = fechaCreacion, onValueChange ={fechaCreacion = it} )
        HorizontalDivider()
        Text(text = "Movimientos")
        LazyColumn {
            uis.value.cuenta?.let {
                items(it.movimientos){
                    Text(text = "${it.fecha} : ${it.monto}")
                }
                // nueva operacion

            }

        }
        FloatingActionButton(onClick = {
            onTransaccion(true)
        }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }

        HorizontalDivider()
        Button(onClick = {
            if(uis.value.cuenta != null) {
                val cuenta = uis.value.cuenta?.copy(
                    numeroCuenta = numeroCuenta,
                    fechaCreacion = fechaCreacion,
                    saldo = saldo
                    )
                vm.crearOActualizarCuenta(cuenta!!)
                onGuardar(true)

            }
        }) {
            Text(text = "Guardar")
        }

        Button(onClick = {

            onGuardar(false)
        }) {
            Text(text = "Cancelar")
        }
    }

}