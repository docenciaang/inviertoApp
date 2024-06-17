package vikas.eu.inviertoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import vikas.eu.inviertoapp.ui.pantallas.PHome
import vikas.eu.inviertoapp.ui.theme.InviertoAppTheme
import vikas.eu.inviertoapp.viewmodel.InvViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            InviertoAppTheme {
                  InviertoApp()
                //PHome()
            }
        }
    }
}
