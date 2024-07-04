package vikas.eu.inviertoapp.ui.componentes

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date



@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HoraPicker(
    label: String="Elige hora",
    onSelected: (LocalDateTime) -> Unit,

    ) {

    var showTimePicker by remember { mutableStateOf(true) }
    val stateTimePicker = rememberTimePickerState()
    val formatter = remember { DateTimeFormatter.ofPattern("HH:mm"); }
    val snackState = remember { SnackbarHostState() }
    val showingPicker = remember { mutableStateOf(true) }
    val snackScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current

    var dateTime = remember { mutableStateOf(LocalDateTime.now()) }
    val dateState = rememberDatePickerState()

    if (showTimePicker) {
        TimePickerDialog(
            title = if (showingPicker.value) {
                "Select Time "
            } else {
                "Enter Time"
            },
            onCancel = { showTimePicker = false },
            onConfirm = {
                dateState.selectedDateMillis?.let {
                    val selectedDate = Instant
                        .ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    dateTime.value = dateTime.value.with(selectedDate)
                }

                dateTime.value = dateTime.value.withHour(stateTimePicker.hour).withMinute(stateTimePicker.minute)

                onSelected(dateTime.value)

                // TODO: VER SNACKBAR
                snackScope.launch {
                    snackState.showSnackbar("Entered time:${dateTime.value} -> ${formatter.format( dateTime.value)}")
                }
                showTimePicker = false

                // onSelected(cal.time)
            },
            toggle = {
                if (configuration.screenHeightDp > 400) {
                    IconButton(onClick = { showingPicker.value = !showingPicker.value }) {
                        val icon = if (showingPicker.value) {
                            Icons.Outlined.KeyboardArrowUp //  Keyboard
                        } else {
                            Icons.Outlined.AddCircle  //Schedule
                        }
                        Icon(
                            icon,
                            contentDescription = if (showingPicker.value) {
                                "Switch to Text Input"
                            } else {
                                "Switch to Touch Input"
                            }
                        )
                    }
                }
            }
        ) {
            if (showingPicker.value && configuration.screenHeightDp > 400) {
                TimePicker(state = stateTimePicker)
            } else {
                TimeInput(state = stateTimePicker)
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HoraPicker2(
    label: String="Elige hora",
    onSelected: (LocalDateTime) -> Unit,

    ) {

    var showTimePicker by remember { mutableStateOf(false) }
    val stateTimePicker = rememberTimePickerState()
    val formatter = remember { DateTimeFormatter.ofPattern("HH:mm"); }
    val snackState = remember { SnackbarHostState() }
    val showingPicker = remember { mutableStateOf(true) }
    val snackScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current

    var dateTime = remember { mutableStateOf(LocalDateTime.now()) }
    val dateState = rememberDatePickerState()



    Box(propagateMinConstraints = false) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = { showTimePicker = true }
        ) {
            Text("Set Time")
        }
        SnackbarHost(hostState = snackState)
    }

    Text(
        text= "${stateTimePicker.hour}:${stateTimePicker.minute}"
    )

    if (showTimePicker) {
        TimePickerDialog(
            title = if (showingPicker.value) {
                "Select Time "
            } else {
                "Enter Time"
            },
            onCancel = { showTimePicker = false },
            onConfirm = {
                dateState.selectedDateMillis?.let {
                    val selectedDate = Instant
                        .ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    dateTime.value = dateTime.value.with(selectedDate)
                }

                dateTime.value = dateTime.value.withHour(stateTimePicker.hour).withMinute(stateTimePicker.minute)

                onSelected(dateTime.value)

                // TODO: VER SNACKBAR
                snackScope.launch {
                    snackState.showSnackbar("Entered time:${dateTime.value} -> ${formatter.format( dateTime.value)}")
                }
                showTimePicker = false

                // onSelected(cal.time)
            },
            toggle = {
                if (configuration.screenHeightDp > 400) {
                    IconButton(onClick = { showingPicker.value = !showingPicker.value }) {
                        val icon = if (showingPicker.value) {
                            Icons.Outlined.KeyboardArrowUp //  Keyboard
                        } else {
                            Icons.Outlined.AddCircle  //Schedule
                        }
                        Icon(
                            icon,
                            contentDescription = if (showingPicker.value) {
                                "Switch to Text Input"
                            } else {
                                "Switch to Touch Input"
                            }
                        )
                    }
                }
            }
        ) {
            if (showingPicker.value && configuration.screenHeightDp > 400) {
                TimePicker(state = stateTimePicker)
            } else {
                TimeInput(state = stateTimePicker)
            }
        }
    }
}




@Composable
fun TimePickerDialog(
    title: String = "Elige hora",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    toggle()
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}