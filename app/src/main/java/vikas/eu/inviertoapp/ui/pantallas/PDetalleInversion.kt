package vikas.eu.inviertoapp.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import vikas.eu.inviertoapp.entidad.Transaccion
import vikas.eu.inviertoapp.viewmodel.InvViewModel


@Composable
fun PDetalleInversion(
    vm: InvViewModel = viewModel(),
    onTransaccion: ( nueva: Boolean) -> Unit
) {
    val uis = vm.uis.collectAsState()



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "        ${uis.value.inversion?.nombreFondo ?: "--"} \n Inicial: ${uis.value.inversion?.monto} €")

        Text(text = " Actual : XXXX")

        HorizontalDivider()

        LazyColumn {
            items(uis.value.inversion?.movimientos ?: emptyList()) {
               // var verDetalle by remember { mutableStateOf(false) }
                Card(onClick = {
                    // uis tiene la inversion para la transaccion
                    vm.guardarTransaccion(it)
                    onTransaccion( false)
                }) {
                    Text(text = "${it.fecha} :  ${it.monto} € : ${it.saldo} €")
                }
            }
        }

        FloatingActionButton(onClick = {
            onTransaccion(true)
        }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }


}


@Composable
fun Detalle(trans: Transaccion) {
    Column {
        Text(text = "${trans.tipo} \n ${trans.fecha} \n ${trans.monto} \n ${trans.origenId} \n ${trans.hashCode()}")

    }
}