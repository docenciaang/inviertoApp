package vikas.eu.inviertoapp.navegacion

import androidx.collection.emptyLongSet
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import vikas.eu.inviertoapp.ui.pantallas.PDetalleCuenta
import vikas.eu.inviertoapp.ui.pantallas.PDetalleInversion
import vikas.eu.inviertoapp.ui.pantallas.PHome
import vikas.eu.inviertoapp.ui.pantallas.PNuevaTransaccion
import vikas.eu.inviertoapp.ui.pantallas.PTransaccion
import vikas.eu.inviertoapp.ui.pantallas.PTransaccionCuenta
import vikas.eu.inviertoapp.viewmodel.InvViewModel

@Composable
fun Navegacion(
    padding: PaddingValues,
    navController: NavHostController,
) {
    val vm = InvViewModel()

    NavHost(
        modifier = Modifier.padding(padding),
        navController = navController, startDestination = Rutas.HOME.ruta
    ) {
        composable(Rutas.HOME.ruta) {
            PHome(
                vm = vm,
                onSelectInversion = { navController.navigate(Rutas.DETALLE_INVERSION.ruta) },
                onSelectCuenta = { navController.navigate(Rutas.DETALLE_CUENTA.ruta) }
            )
        }
        composable(Rutas.DETALLE_INVERSION.ruta) {
            PDetalleInversion(vm) { nuevaTransaccion ->
                if (nuevaTransaccion)
                    navController.navigate(Rutas.NUEVA_TRANSACCION.ruta)
                else
                    navController.navigate(Rutas.TRANSACCION.ruta)
            }
        }
        composable(Rutas.TRANSACCION.ruta) {
            PTransaccion(vm) {
                navController.navigateUp()
            }
        }
        composable(Rutas.NUEVA_TRANSACCION.ruta) {
            PTransaccion(vm) {
                navController.navigateUp()
            }
        }

        composable(Rutas.DETALLE_CUENTA.ruta) {
            PDetalleCuenta(
                vm,
                onTransaccion = { esNuevaTransaccion ->
                    if (esNuevaTransaccion)
                        navController.navigate(Rutas.TRANSACCION_CUENTA.ruta)
                    else
                        navController.navigate(Rutas.TRANSACCION_CUENTA.ruta)


                },
                onGuardar = { seGuarda ->
                    navController.navigate(Rutas.HOME.ruta)
                })
        }
        composable(Rutas.TRANSACCION_CUENTA.ruta){
            PTransaccionCuenta(vm) {

                navController.navigateUp()

            }
        }

    }
}








