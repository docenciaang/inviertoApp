package vikas.eu.inviertoapp.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.LaunchedEffect
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

/**
 * Ver y editar inversoin
 * Si el id de la inversion es null, es una creación de inversion
 *
 */
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

        Text(text = "        ${uis.value.inversion?.nombreFondo ?: "--"}")

        Text(text = " Actual: ${uis.value.inversion?.monto} € \n Inicial : ${uis.value.inversion?.movimientos?.first()?.monto} €")

        HorizontalDivider()

        LazyColumn {
            items(uis.value.inversion?.movimientos ?: emptyList()) {
               // var verDetalle by remember { mutableStateOf(false) }
                Detalle(trans = it ) {
                    // uis tiene la inversion para la transaccion
                    vm.setTransaccion(it)
                    onTransaccion( false)
                }


            }
        }

        FloatingActionButton(onClick = {
            vm.nuevaTransaccion()
            onTransaccion(true)
        }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }


}


@Composable
fun Detalle(
    trans: Transaccion,
    onPulsado: () -> Unit
    ) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onPulsado
    ) {
        Text(text = "${trans.tipo} : ${trans.fecha} : ${trans.monto} € ${trans.saldo}  €")
    }

}