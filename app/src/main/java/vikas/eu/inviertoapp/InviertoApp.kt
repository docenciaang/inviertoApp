package vikas.eu.inviertoapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import vikas.eu.inviertoapp.navegacion.BarraNavegacion
import vikas.eu.inviertoapp.navegacion.InvTopAppBar
import vikas.eu.inviertoapp.navegacion.Navegacion
import vikas.eu.inviertoapp.navegacion.Rutas

@Composable
fun InviertoApp() {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    var pantallaActual by remember { mutableStateOf("") }

    DisposableEffect(key1 = true) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            pantallaActual = destination.route ?: "Desconocido"
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose { navController.removeOnDestinationChangedListener(listener) }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            InvTopAppBar(nav = navController,pantallaActual)
        },
        bottomBar = {
            BarraNavegacion(nav = navController,pantallaActual)
        }
    ){
        Navegacion(it, navController)
    }
}