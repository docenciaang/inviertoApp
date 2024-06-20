package vikas.eu.inviertoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import vikas.eu.inviertoapp.entidad.Cuenta
import vikas.eu.inviertoapp.entidad.Inversion
import vikas.eu.inviertoapp.entidad.Transaccion
import vikas.eu.inviertoapp.red.ApiService.cuentaService
import vikas.eu.inviertoapp.red.ApiService.inversionService
import vikas.eu.inviertoapp.red.ApiService.transaccionesService


class InvViewModel: ViewModel() {
    private val _uis = MutableStateFlow(InviertoUIState())
    val uis = _uis.asStateFlow()

    init{
        this.getDatos()
    }
     fun getDatos(){
        viewModelScope.launch(Dispatchers.IO){
            val res =inversionService.getAll()
            if (res.isSuccessful){
                _uis.update { it.copy(listaInversiones = res.body() ?: emptyList() ) }
            }
            val resCC = cuentaService.getAll()
            if (resCC.isSuccessful){
                _uis.update { it.copy(listaCuentas = resCC.body() ?: emptyList() ) }
            }
        }
    }


    /**
     * Traemos detalle de la inversion
     */
    fun setInversion(inv: Inversion) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = inversionService.getDetalles(inv.id)
            if (res.isSuccessful){
                _uis.update { it.copy(inversion = res.body()) }
            }
        }

    }

//    fun getTransacciones(id: Long){
//        viewModelScope.launch(Dispatchers.IO) {
//        }
//    }
    fun setTransaccion( trans : Transaccion) {
        _uis.update { it.copy(transaccion = trans) }
    }

    /**
     * Transaccion nueva o modificada
     * TODO: ¿volver a pedir inversion detalle?
     */
    fun guardarTransaccion(trans : Transaccion) {
        viewModelScope.launch(Dispatchers.IO) {
            if(trans.id == null || trans.id == 0L) {
                val res = transaccionesService.insertar(trans)
            if (res.isSuccessful){
                _uis.update { it.copy(transaccion = trans) }
            }
            } else{
                // put
                val res = transaccionesService.actualizar(trans.id!!, trans)
                if (res.isSuccessful){
                    _uis.update { it.copy(transaccion = trans) }
                }
            }
        }

    }

    fun nuevaTransaccion() {
        val trans = Transaccion()
        trans.id = 0
        _uis.update { it.copy(transaccion = trans)}
    }

    /**
     * ===============================================
     *  CUENTAS
     * =================================================
     **/

    fun setCuenta(cc: Cuenta) {
        if(cc.id != null && cc.id != 0L) {
            viewModelScope.launch(Dispatchers.IO) {
                val res = cc.id?.let { cuentaService.getDetalles(it) }
                if (res != null) {
                    if (res.isSuccessful) {
                        _uis.update { it.copy(cuenta = res.body()) }
                    }
                }
            }
        } else
            _uis.update { it.copy(cuenta = cc) }

    }

    fun crearCuenta(cuenta: Cuenta) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = cuentaService.insertar(cuenta)
            if (res.isSuccessful) {
                cuenta.id = res.body()?.id
                _uis.update { it.copy(cuenta = cuenta) }
            }
        }
    }


}