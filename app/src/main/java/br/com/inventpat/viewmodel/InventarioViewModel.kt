package br.com.invpatrim.viewmodel

import android.R
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.inventpat.model.inventario.Inventario
import br.com.inventpat.model.inventario.InventarioRepository
import br.com.inventpat.util.Event

class InventarioViewModel(private val repository: InventarioRepository) : ViewModel(), Observable {

    val inventarios = repository.inventarios
    private var isUpdateOrDelete = false
    private lateinit var inventarioToUpdateOrDelete: Inventario

    @Bindable
    val inputCodigo = MutableLiveData<String>()

    @Bindable
    val inputDescricao = MutableLiveData<String>()

    @Bindable
    val inputContagem1 = MutableLiveData<String>()

    @Bindable
    val inputContagem2 = MutableLiveData<String>()

    @Bindable
    val inputContagem3 = MutableLiveData<String>()

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

    var btnSalvar = MutableLiveData<Boolean>()

    var btnIncluir = MutableLiveData<Boolean>()

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
    val myStrings = arrayOf("001 - Matriz", "010 - Filial 10", "015 - Filial 15")

    init {
        clearAll()
        incluirButtonText.value = "Incluir"
        excluirButtonText.value = "Excluir"
        btnSalvar.value = false
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
        spEnd1.value = null
        spEnd2.value = null
        spEnd3.value = null
        spEnd4.value = null
        spEnd5.value = null
        spEnd6.value = null
        inputFabr.value = null
        inputValid.value = null
        inputContagem1.value = null
        inputContagem2.value = null
        inputContagem3.value = null
        //N.Fiscal: String
        //valor: Double
        inputTecnico.value = null
        inputFoto.value = null
    }

    fun incluirItem() {
        clearAll()
        btnSalvar.value = true
        btnIncluir.value = false
        btnExcluir.value = false
    }

    fun saveOrUpdate() {
        if (inputCodigo.value.isNullOrBlank()) {
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
                val cod = inputCodigo.value!!
                val emp = 0
                val unidNeg = ""
                val local = spLocal.value!!
                val descr = inputDescricao.value!!
                val unidMed = spUnidMed.value!!
                val end1 = spEnd1.value!!
                val end2 = spEnd2.value!!
                val end3 = spEnd3.value!!
                val end4 = spEnd4.value!!
                val end5 = spEnd5.value!!
                val end6 = spEnd6.value!!
                val fabr = inputFabr.value!!
                val valid = inputValid.value!!
                val contagem1 = inputContagem1.value!!
                val contagem2 = inputContagem2.value!!
                val contagem3 = inputContagem3.value!!
                val nf = ""
                val valor = 0.00
                val tecnico = inputTecnico.value!!
                val foto = inputFoto.value!!
                insert(Inventario(cod, emp, unidNeg, local, descr, unidMed, end1, end2, end3, end4,
                    end5, end6, fabr, valid, contagem1, contagem2, contagem3, nf,
                    valor, tecnico, foto))
                posicionarItemSalvo()
            }
        }
    }

    fun posicionarItemSalvo() {
        clearAll()
        btnSalvar.value = false
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
        inputContagem1.value = inventario.contagem1.toString()
        inputContagem2.value = inventario.contagem2.toString()
        inputContagem3.value = inventario.contagem3.toString()
        inputFabr.value = inventario.fabricacao.toString()
        inputValid.value = inventario.validade.toString()
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