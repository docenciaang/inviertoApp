package vikas.eu.inviertoapp.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class StatusUIState(
    val msg: String="",
)
@HiltViewModel
class StatusViewModel @Inject constructor(

) : ViewModel() {

    private val _uis = MutableStateFlow(StatusUIState())
    val uis = _uis.asStateFlow()

    fun set(mensaje: String){
        _uis.update { it.copy(msg=mensaje) }
    }

}