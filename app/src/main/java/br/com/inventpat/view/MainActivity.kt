package br.com.inventpat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import br.com.inventpat.R
import br.com.inventpat.databinding.ActivityMainBinding
import br.com.invpatrim.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        binding.lifecycleOwner = this
        binding.inventario = viewModel

     //   spinnerLoadUnid()
    }

    fun spinnerLoadUnid() {
        //String array.
        val myStrings = arrayOf("011 - kg", "001 - litro", "002 - unidade", "017 - caixa")
        //Adapter for spinner
       // spinnerUnid.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, myStrings)
      //  viewModel.spinnerUnid = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, myStrings)
    }


}