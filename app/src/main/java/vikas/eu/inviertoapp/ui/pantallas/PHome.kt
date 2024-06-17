package vikas.eu.inviertoapp.ui.pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import vikas.eu.inviertoapp.viewmodel.InvViewModel

@Composable
fun PHome(
    vm: InvViewModel = viewModel(),
    onSelect: () -> Unit
){
    val uis = vm.uis.collectAsState()
    var contador by remember{ mutableIntStateOf(1) }


    LaunchedEffect(contador) {
        vm.getInversiones()
    }
    Column {
        Text(text="Ventana Home")

        Button(onClick = { contador++ }) {
            Text(text = "Pedir inversiones")
        }
        LazyColumn {
            items(uis.value.listaInversiones ){
                Card(
                    onClick ={
                        vm.setInversion(it)
                        onSelect()}
                ) {
                    Text(text = "${it.nombreFondo ?: "--"} ${it.fechaInversion} : ${uis.value.inversion?.monto}")

                }



            }
        }
    }
}