package br.com.inventpat

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.inventpat.view.MainActivity

class ExportViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is export Fragment"
    }
    val text: LiveData<String> = _text

    fun sair(view: View) {
        Log.i("INFO","SAIR")
    }
}