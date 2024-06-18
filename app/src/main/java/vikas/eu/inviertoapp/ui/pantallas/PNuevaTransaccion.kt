package vikas.eu.inviertoapp.ui.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
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
import vikas.eu.inviertoapp.viewmodel.InvViewModel

/**
 * ===============================================
 *  NUEVA TRANSACCION
 *  en la ventana de detalle de inversion se crea una transacción
 *  nueva
 * =================================================
 **/

@Composable
fun PNuevaTransaccion(
    vm: InvViewModel = viewModel(),
    onContinuar: () -> Unit
) {
    var tipo by remember{ mutableStateOf(TipoTransaccion.AJUSTE) }

    Column {
        Text("Elegir tipo de transacción")
        Text(text = "Pruebas elegido =$tipo")
        ExposedDropdownMenuSample(
            label="Tipo transaccion",
            options = TipoTransaccion.values().toList(),
            ){ elegido: TipoTransaccion ->
               tipo = elegido
        }
        Button(onClick = {
            val trans = Transaccion()
            trans.tipo = tipo
            vm.setTransaccion(trans)
            onContinuar()
        }) {
            Text("Continuar")
        }
    }
}



fun <T : Enum<T>> enumToStringList(enumClass: Class<T>): List<String> {
    return enumClass.enumConstants.map { it.name }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun <T : Enum<T>> ExposedDropdownMenuSample(
    label: String,
    options: List<T>,
    onOptionSelected: (T) -> Unit
) {
    val optionStrings = options.map { it.name }
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(optionStrings[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            value = text,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            optionStrings.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        text = option
                        expanded = false
                        onOptionSelected(options[index])
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
