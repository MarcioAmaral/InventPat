package br.com.invpatrim.viewmodel

import android.R
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    val spinnerUnid = MutableLiveData<Array<String>>()

    val codigo = MutableLiveData<String>()
    val descricao = MutableLiveData<String>()
    val fabricacao = MutableLiveData<String>()
    val tecnico = MutableLiveData<String>()
    init {
        codigo.value = "001"
        descricao.value = "Mesa com tampo de vidro fume c/4 cadeiras"
        fabricacao.value = "17/09/20"
        tecnico.value = "Jose"
    }

    lateinit var spinner: Spinner
    lateinit var arrayAdapter: ArrayAdapter<String>



    fun spinnerLoadUnid() {
        //String array.
        val myStrings = arrayOf("011 - kg", "001 - litro", "002 - unidade", "017 - caixa")
        //Adapter for spinner
       // spinnerUnid.adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, myStrings)
        spinnerUnid.value=myStrings
    }

}