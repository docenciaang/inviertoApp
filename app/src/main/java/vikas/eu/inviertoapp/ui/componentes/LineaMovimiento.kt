package vikas.eu.inviertoapp.ui.componentes

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import vikas.eu.inviertoapp.entidad.Transaccion

/**
 * Linea de transaccion
 */

@Composable
fun LineaCabecera(){
    Row {
        Text(
            modifier = Modifier.weight(2f),
            //   textAlign = TextAlign.Start,
            text = "(Operación) ",
            maxLines = 1
        )
        Text(
            modifier = Modifier.weight(2f),
            //   textAlign = TextAlign.Start,
            text = " : Fecha".take(11),
            maxLines = 1
        )
        Text(
            modifier = Modifier.weight(1f),
            //   textAlign = TextAlign.Start,
            text = " : Monto"
        )
        Text(
            modifier = Modifier.weight(1f),
            //   textAlign = TextAlign.Start,
            text = " : Saldo"
        )
    }
}
@Composable
fun LineaMovimiento(trans: Transaccion) {
    Row {
        Text(
            modifier = Modifier.weight(2f),
            //   textAlign = TextAlign.Start,
            text = "(${trans.tipo})",
            maxLines = 1
        )
        Text(
            modifier = Modifier.weight(2f),
            //   textAlign = TextAlign.Start,
            text = " : ${trans.fecha}".take(13),
            maxLines = 1
        )
        Text(
            modifier = Modifier.weight(1f),
            //   textAlign = TextAlign.Start,
            text = "${trans.monto}",
            textAlign = TextAlign.End
        )
        Text(
            modifier = Modifier.weight(1f),
            //   textAlign = TextAlign.Start,
            text = "${trans.saldo}",
            textAlign = TextAlign.End
        )
    }
}