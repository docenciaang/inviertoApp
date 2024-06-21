package vikas.eu.inviertoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import vikas.eu.inviertoapp.ui.pantallas.PHome
import vikas.eu.inviertoapp.ui.theme.InviertoAppTheme
import vikas.eu.inviertoapp.viewmodel.InvViewModel
import vikas.eu.inviertoapp.viewmodel.StatusViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val exampleViewModel: StatusViewModel by viewModels()

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
