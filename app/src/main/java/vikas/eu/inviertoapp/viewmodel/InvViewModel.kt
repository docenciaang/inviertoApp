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



    fun detalleInversion(){
       // if (uis.value.inversion != null && uis.value.inversion!!.id !=  null )
        viewModelScope.launch(Dispatchers.IO) {
            val res = _uis.value.inversion?.id?.let { inversionService.getDetalles(it) }
            if (res?.isSuccessful == true){
                _uis.update { it.copy(inversion = res.body()) }
            }
        }
    }
    /**
     * Traemos detalle de la inversion
     */
    fun setInversion(inv: Inversion) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = inv.id?.let { inversionService.getDetalles(it) }
            if (res?.isSuccessful == true){
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
                if (trans.id == 0L)
                    trans.id = null // espera null para insertar
                val res = transaccionesService.insertar(trans)
            if (res.isSuccessful){
                _uis.update { it.copy(transaccion = trans) }
                detalleInversion()
            }
            } else{
                // put
                val res = transaccionesService.actualizar(trans.id!!, trans)
                if (res.isSuccessful){
                    _uis.update { it.copy(transaccion = trans) }
                    detalleInversion()
                }
            }
        }

    }

    /**
     * Nueva transaccion usando como origen la inversion
     */
    fun nuevaTransaccion() {
        val trans = Transaccion()
        trans.id = 0
        _uis.update { it.copy(transaccion = trans)}
    }


    /**
     * Obtiene detalle de uis.cuenta
     */
    fun detalleCuenta(){
         if (uis.value.cuenta != null && uis.value.cuenta!!.id !=  null  && uis.value.cuenta!!.id != 0L)
             viewModelScope.launch(Dispatchers.IO) {
                 val res = _uis.value.cuenta?.id?.let { cuentaService.getDetalles(it) }
            if (res?.isSuccessful == true){
                _uis.update { it.copy(cuenta = res.body()) }
            }
        }
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

    fun nuevaCuenta(){
        _uis.update { it.copy(cuenta = Cuenta()) }
    }

    /** Crea o modifica una cuenta
     *
     */
    fun crearOActualizarCuenta(cuenta: Cuenta) {
        viewModelScope.launch(Dispatchers.IO) {
            if( cuenta.id == null ) {
                val res = cuentaService.insertar(cuenta)
                if (res.isSuccessful) {
                    cuenta.id = res.body()?.id
                    _uis.update { it.copy(cuenta = cuenta) }
                }
            } else{
                val resUpdate = cuentaService.actualizar(cuenta.id!!, cuenta)
                if (resUpdate.isSuccessful){
                    _uis.update { it.copy(cuenta = resUpdate.body()) }
                }
            }
        }
    }

    fun nuevaInversion(){
        _uis.update { it.copy(inversion = Inversion()) }
    }
    fun crearInversion(inversion: Inversion) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = inversionService.insertar(inversion)
            if (res.isSuccessful) {
                inversion.id = res.body()?.id!!
                _uis.update { it.copy(inversion = inversion) }
            }
        }
    }


}