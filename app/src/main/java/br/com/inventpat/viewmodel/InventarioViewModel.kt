package br.com.invpatrim.viewmodel

import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.inventpat.model.inventario.Inventario
import br.com.inventpat.model.inventario.InventarioRepository
import br.com.inventpat.util.Event
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class InventarioViewModel(private val repository: InventarioRepository) : ViewModel(), Observable {

    val inventarios = repository.inventarios
    private var isUpdateOrDelete = false
    private lateinit var inventarioToUpdateOrDelete: Inventario

    @Bindable
    val inputCodigo = MutableLiveData<String?>()

    @Bindable
    val inputDescricao = MutableLiveData<String>()

    @Bindable
    val inputCont1 = MutableLiveData<String>()

    @Bindable
    val inputCont2 = MutableLiveData<String>()

    @Bindable
    val inputCont3 = MutableLiveData<String>()

    @Bindable
    val inputFabr = MutableLiveData<String>()

    @Bindable
    val inputValid = MutableLiveData<String>()

    @Bindable
    val inputTecnico = MutableLiveData<String>()

    @Bindable
    val inputFoto = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    @Bindable
    val incluirButtonText = MutableLiveData<String>()

    @Bindable
    val excluirButtonText = MutableLiveData<String>()
    @Bindable
    var btnSalvar = MutableLiveData<Boolean>()
    @Bindable
    var btnFoto = MutableLiveData<Boolean>()
    @Bindable
    var btnIncluir = MutableLiveData<Boolean>()
    @Bindable
    var btnExcluir = MutableLiveData<Boolean>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    var spLocal = MutableLiveData<String>()
    var spUnidMed = MutableLiveData<String>()
    var spEnd1 = MutableLiveData<String>()
    var spEnd2 = MutableLiveData<String>()
    var spEnd3 = MutableLiveData<String>()
    var spEnd4 = MutableLiveData<String>()
    var spEnd5 = MutableLiveData<String>()
    var spEnd6 = MutableLiveData<String>()

    init {
        clearAll()
        incluirButtonText.value = "Incluir"
        excluirButtonText.value = "Excluir"
        btnSalvar.value = false
        btnFoto.value = false
        btnIncluir.value = true
        btnExcluir.value = false
    }

    fun clearAll() {
        inputCodigo.value = null
        //empresa: Int
        //unid.negócio: String
        spLocal.value = null
        inputDescricao.value = null
        spUnidMed.value = null
        spEnd1.value = ""
        spEnd2.value = ""
        spEnd3.value = ""
        spEnd4.value = ""
        spEnd5.value = ""
        spEnd6.value = ""
        inputFabr.value = ""
        inputValid.value = ""
        inputCont1.value = ""
        inputCont2.value = ""
        inputCont3.value = ""
        //N.Fiscal: String
        //valor: Double
        inputTecnico.value = ""
        inputFoto.value = ""
    }

    fun incluirItem() {
        clearAll()
        btnSalvar.value = true
        btnFoto.value = true
        btnIncluir.value = false
        btnExcluir.value = false
    }

    fun saveOrUpdate() = if (inputCodigo.value.isNullOrBlank()) {
        statusMessage.value = Event("Por favor informe o código")
    } else if (spLocal.value.isNullOrBlank()) {
        statusMessage.value = Event("Por favor informe o local")
    } else if (inputDescricao.value.isNullOrBlank()) {
        statusMessage.value = Event("Por favor informe a descrição")
    } else if (inputTecnico.value.isNullOrBlank()) {
        statusMessage.value = Event("Por favor informe o técnico")
    } else if (spUnidMed.value.isNullOrBlank()) {
        statusMessage.value = Event("Por favor informe a unidade")
    } else {
        if (isUpdateOrDelete) {
            /*subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!
            update(subscriberToUpdateOrDelete)*/
            statusMessage.value = Event("Atualizar ou Excluir item - confirmar exclusão")
            posicionarItemSalvo()
        } else {
            val cod: String = inputCodigo.value!!.trim()
            val emp = 0
            val unidNeg = ""
            val local = spLocal.value!!
            val descr = inputDescricao.value!!.trim()
            val unidMed = spUnidMed.value!!
            val end1 = spEnd1.value!!
            val end2 = spEnd2.value!!
            val end3 = spEnd3.value!!
            val end4 = spEnd4.value!!
            val end5 = spEnd5.value!!
            val end6 = spEnd6.value!!
            val fabr = inputFabr.value!!.trim()
            val valid = inputValid.value!!.trim()
            val contagem1 = inputCont1.value!!.trim()
            val contagem2 = inputCont2.value!!.trim()
            val contagem3 = inputCont3.value!!.trim()
            val nf = ""
            val valor = 0.00
            val tecnico = inputTecnico.value!!.trim()
            val foto = inputFoto.value!!.trim()
            insert(
                Inventario(
                    cod,
                    emp,
                    unidNeg,
                    local,
                    descr,
                    unidMed,
                    end1,
                    end2,
                    end3,
                    end4,
                    end5,
                    end6,
                    fabr,
                    valid,
                    contagem1,
                    contagem2,
                    contagem3,
                    nf,
                    valor,
                    tecnico,
                    foto
                )
            )
            posicionarItemSalvo()
        }
    }

    fun insert(inventario: Inventario) = viewModelScope.launch {
        var newRowId: Long
        withContext(Dispatchers.IO) {
           newRowId = repository.insert(inventario)
        }
        if(newRowId>-1) {
            statusMessage.value = Event("Item inserido com sucesso $newRowId")
        }else{
            statusMessage.value = Event("Ocorreu um erro, item não inserido!")
        }
    }

    private fun posicionarItemSalvo() {
        clearAll()
        btnSalvar.value = false
        btnFoto.value = false
        btnIncluir.value = true
        btnExcluir.value = false
        isUpdateOrDelete = false
    }

    fun excluirItem() {
        statusMessage.value = Event("Excluir item - confirmar exclusão")
    }

    fun initUpadetAndDelete(inventario: Inventario) {
        inputCodigo.value = inventario.inventarioId.toString()
        inputDescricao.value = inventario.descricao
        inputCont1.value = inventario.contagem1.toString()
        inputCont2.value = inventario.contagem2.toString()
        inputCont3.value = inventario.contagem3.toString()
        inputFabr.value = inventario.fabricacao.toString()
        inputValid.value = inventario.validade.toString()
        inputFoto.value = inventario.foto
        inputTecnico.value = inventario.tecnico
        isUpdateOrDelete = true
        inventarioToUpdateOrDelete = inventario
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}