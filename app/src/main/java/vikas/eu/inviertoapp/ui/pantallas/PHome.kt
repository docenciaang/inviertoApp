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
import vikas.eu.inviertoapp.viewmodel.StatusViewModel

@Composable
fun PHome(
    vm: InvViewModel = viewModel(),
    vmStatus : StatusViewModel = hiltViewModel(),
    onSelectInversion: () -> Unit,
    onSelectCuenta: () -> Unit
){
    val uis = vm.uis.collectAsState()
    var contador by remember{ mutableIntStateOf(1) }

  //  val vmStatus : StatusViewModel = hiltViewModel()


    LaunchedEffect(key1= Unit) {
        vm.getDatos()
    }
    Column {
        Text(text="Ventana Home")

       Button(onClick = { contador++ }) {
           Text(text = "Pedir datos")
      }
        LazyColumn {
            item{
                Text(text = "CUENTAS")
            }
            items(uis.value.listaCuentas){
                Card(
                    onClick ={
                        vm.setCuenta(it)
                        onSelectCuenta()}
                ) {
                    Text(text = "${it.numeroCuenta ?: "--"} ${it.fechaCreacion} : ${it.saldo}")

                }
            }
            item{
                Text("INVERSIONES")
            }
            items(uis.value.listaInversiones ){
                Card(
                    onClick ={
                        vm.setInversion(it)
                        onSelectInversion()}
                ) {
                    Text(text = "${it.nombreFondo ?: "--"} ${it.fechaInversion} : ${it.monto}")

                }



            }
        }
    }
}