package vikas.eu.inviertoapp.ui.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import vikas.eu.inviertoapp.entidad.Cuenta
import vikas.eu.inviertoapp.viewmodel.InvViewModel
import java.time.LocalDate

@Composable
fun PNuevaCuenta(
    vm: InvViewModel,
    onCreado: () -> Unit
){
    var numCC by remember{ mutableStateOf("") }
    var saldoIncial by remember { mutableStateOf("") }
//    LaunchedEffect(key1= Unit) {
//        vm.setCuenta(Cuenta())
//    }

    Column {
        Text(text = "Nueva Cuenta")
        TextField(value = numCC, onValueChange ={numCC = it}, label={ Text(text = "num cc")} )
        TextField(value = saldoIncial, onValueChange ={saldoIncial = it}, label={ Text(text = "num cc")} )
        Button(onClick = {
            vm.crearCuenta(Cuenta(
                numeroCuenta = numCC,
                saldo = saldoIncial.toDoubleOrNull() ,
                fechaCreacion = LocalDate.now().toString()
            ))
            onCreado()
        }) {
            Text("Crear")
        }
    }
}