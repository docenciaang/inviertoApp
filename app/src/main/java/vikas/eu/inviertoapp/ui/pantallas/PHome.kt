package vikas.eu.inviertoapp.ui.pantallas

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import vikas.eu.inviertoapp.entidad.Cuenta
import vikas.eu.inviertoapp.entidad.Inversion
import vikas.eu.inviertoapp.viewmodel.InvViewModel

@Composable
fun PHome(
    vm: InvViewModel = viewModel(),
    onSelectInversion: () -> Unit,
    onSelectCuenta: () -> Unit
){
    val uis = vm.uis.collectAsState()
    var contador by remember{ mutableIntStateOf(1) }


    LaunchedEffect(key1= Unit) {
        vm.getDatos()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

       Button(onClick = { contador++ }) {
           Text(text = "Pedir datos")
      }
        LazyColumn {
            item{
                Card {


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Cuentas")
                        IconButton(onClick = {
                            vm.nuevaCuenta()
                            onSelectCuenta()
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "nueva cuenta")
                        }
                    }
                }


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
                Card {


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Inversiones")
                        IconButton(onClick = {
                            vm.nuevaInversion()
                            onSelectCuenta()
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "nueva cuenta")
                        }
                    }
                }

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