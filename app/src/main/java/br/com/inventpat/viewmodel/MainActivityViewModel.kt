package br.com.invpatrim.viewmodel

import android.R
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    var spinnerUnid = MutableLiveData<Array<String>>()


    fun spinnerLoadUnid() {
        //String array.
        val myStrings = arrayOf("011 - kg", "001 - litro", "002 - unidade", "017 - caixa")
        //Adapter for spinner
       // spinnerUnid.adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, myStrings)
        spinnerUnid.value=myStrings
    }

}