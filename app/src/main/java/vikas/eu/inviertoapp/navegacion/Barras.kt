package vikas.eu.inviertoapp.navegacion

import androidx.compose.material.icons.Icons
import androidx.compose.foundation.Image
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController


// Definir la clase sellada para los iconos
sealed class NavigationIcon {
    data class VectorIcon(val imageVector: ImageVector) : NavigationIcon()
    data class DrawableIcon(val recursoId: Int) : NavigationIcon()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvTopAppBar(nav: NavController, pantallaActual: String) {
    TopAppBar(
        title = { Text(text = pantallaActual) },
        navigationIcon = {
            IconButton(onClick = { nav.navigateUp() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
            }
        },
        actions = {
            IconButton(onClick = { nav.navigate(Rutas.HOME.ruta) }) {
                Icon(Icons.Default.Home, contentDescription = "Menu")
            }
        })
}


data class BottomNavigationItemTransaccion(
    val ruta: String = "",
    val etiqueta: String = "",
    val icono: NavigationIcon = NavigationIcon.VectorIcon(Icons.Filled.Home)
) {
    fun dameNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                Rutas.HOME.ruta,
                etiqueta = "Home",
                icono = NavigationIcon.VectorIcon(Icons.Filled.Home)
            ),

        )
    }
}

data class BottomNavigationItem(
    val ruta: String = "",
    val etiqueta: String = "",
    val icono: NavigationIcon = NavigationIcon.VectorIcon(Icons.Filled.Home)
) {
    fun dameNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                Rutas.HOME.ruta,
                etiqueta = "Home",
                icono = NavigationIcon.VectorIcon(Icons.Filled.Home)
            ),
            BottomNavigationItem(
                Rutas.NUEVA_CUENTA.ruta,
                etiqueta = "Nueva CC",
                icono = NavigationIcon.VectorIcon(Icons.Filled.Add)
            ),
            BottomNavigationItem(
                Rutas.DETALLE_INVERSION.ruta,
                etiqueta = "Inversiones",
                icono = NavigationIcon.VectorIcon(Icons.Filled.Add)
            )
        )
    }
}

@Composable
fun BarraNavegacion(
    nav: NavHostController,
    pantallaActual: String
) {
    var itemSeleccionado by remember { mutableIntStateOf(0) }

    val items =
        when (pantallaActual) {
            Rutas.TRANSACCION.ruta -> BottomNavigationItemTransaccion().dameNavigationItems()
            Rutas.HOME.ruta -> BottomNavigationItem().dameNavigationItems()
            else -> BottomNavigationItem().dameNavigationItems()
        }


    NavigationBar {
        items.forEachIndexed { index, navigationItem ->

            NavigationBarItem(
                selected = index == itemSeleccionado,
                label = {
                    Text(text = navigationItem.etiqueta)
                },
                onClick = {
                    itemSeleccionado = index
                    nav.navigate(navigationItem.ruta) {
//                        popUpTo(nav.graph.startDestinationId) {
//                            saveState = true
//                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when (navigationItem.icono) {
                        is NavigationIcon.VectorIcon -> Icon(
                            imageVector = (navigationItem.icono as NavigationIcon.VectorIcon).imageVector,
                            contentDescription = navigationItem.etiqueta
                        )

                        is NavigationIcon.DrawableIcon -> Image(
                            painter = painterResource(id = (navigationItem.icono as NavigationIcon.DrawableIcon).recursoId),
                            contentDescription = navigationItem.etiqueta
                        )
                    }
                },

                )

        }
    }
}
