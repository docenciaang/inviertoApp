package vikas.eu.inviertoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import vikas.eu.inviertoapp.ui.pantallas.PHome
import vikas.eu.inviertoapp.ui.theme.InviertoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InviertoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PHome()
                }
            }
        }
    }
}
