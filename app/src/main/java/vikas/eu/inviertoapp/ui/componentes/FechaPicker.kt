package vikas.eu.inviertoapp.ui.componentes


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FechaPicker(
    modifier: Modifier = Modifier,
    label: String,
    conHora: Boolean = false,
    fechaMinima: LocalDateTime =  LocalDateTime.of(1990,1,1,0,0),
    onSelect: (LocalDateTime) -> Unit
) {
    val date = remember { mutableStateOf(LocalDateTime.now()) }
    var isOpen by remember { mutableStateOf(false) }
    val state = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val date = LocalDate.ofEpochDay(utcTimeMillis / 86400000)
                return date >= fechaMinima.toLocalDate()
            }
        }

    )

    var abrirHora by remember{ mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .clickable {isOpen = true  },
        shape = RoundedCornerShape(10.dp),
        readOnly = true,
        enabled = false,
        value = date.value.format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")),
        label = { Text(label) },
        onValueChange = {},
        trailingIcon = {
            IconButton(
                onClick = { isOpen = true }
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Calendar")
            }
        }
    )


    if (isOpen) {
        DatePickerDialog(
            onDismissRequest = { isOpen = false },
            confirmButton = {
                Button(onClick = {
                    isOpen = false
                    state.selectedDateMillis?.let {
                        date.value = Instant.ofEpochMilli(it)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
                        if (conHora)
                            abrirHora= true
                        else
                            onSelect(date.value)
                    }
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                Button(onClick = {
                    isOpen = false

                }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = state)

        }
    }

    if (abrirHora){
        HoraPicker{
            date.value = date.value.withHour(it.hour).withMinute(it.minute)
            abrirHora = false
            onSelect(date.value)
        }
    }
}