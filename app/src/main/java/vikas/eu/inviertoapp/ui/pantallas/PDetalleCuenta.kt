package vikas.eu.inviertoapp.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import vikas.eu.inviertoapp.viewmodel.InvViewModel

@Composable
fun PDetalleCuenta(
    vm: InvViewModel = viewModel(),
    onTransaccion: ( nueva: Boolean) -> Unit
) {
    val uis = vm.uis.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Id. CC.   ${uis.value.cuenta?.numeroCuenta ?: "--"} \n Saldo: ${uis.value.cuenta?.saldo} €")

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

    }

}