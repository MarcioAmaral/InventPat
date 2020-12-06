package br.com.inventpat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.inventpat.R
import br.com.inventpat.databinding.ActivityMainBinding
import br.com.inventpat.model.inventario.Inventario
import br.com.inventpat.model.inventario.InventarioDatabase
import br.com.inventpat.model.inventario.InventarioRepository
import br.com.inventpat.util.Event
import br.com.inventpat.viewmodel.InventarioViewModelFactory
import br.com.invpatrim.viewmodel.InventarioViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var inventarioViewModel: InventarioViewModel
    private lateinit var adapterInvViewHolder: InventRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = InventarioDatabase.getInstance(application).inventarioDao
        val repository = InventarioRepository(dao)
        val factory = InventarioViewModelFactory(repository)
        inventarioViewModel = ViewModelProvider(this, factory).get(InventarioViewModel::class.java)
        binding.viewModel = inventarioViewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        inventarioViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        loadSpinner()

        binding.spinnerLocal.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    inventarioViewModel.spLocal.value = parent.getItemAtPosition(position).toString()
                  //  Toast.makeText(this@MainActivity, "${inventarioViewModel.spLocal.value}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.spinnerUnid.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    inventarioViewModel.spUnid.value = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.spinnerEnd1.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    inventarioViewModel.spEnd1.value = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.spinnerEnd2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    inventarioViewModel.spEnd2.value = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.spinnerEnd3.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    inventarioViewModel.spEnd3.value = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.spinnerEnd4.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    inventarioViewModel.spEnd4.value = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.spinnerEnd5.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    inventarioViewModel.spEnd5.value = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.spinnerEnd6.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    inventarioViewModel.spEnd6.value = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        inventarioViewModel.spLocal.observe(this, Observer {
            if(it.isNullOrBlank()) {
                clearSpinner()
            }
        })
    }

    fun clearSpinner() {
        binding.spinnerLocal.setSelection(0)
        binding.spinnerUnid.setSelection(0)
        binding.spinnerEnd1.setSelection(0)
        binding.spinnerEnd2.setSelection(0)
        binding.spinnerEnd3.setSelection(0)
        binding.spinnerEnd4.setSelection(0)
        binding.spinnerEnd5.setSelection(0)
        binding.spinnerEnd6.setSelection(0)
        binding.idCodigo.requestFocus(0)

    }

    fun initRecyclerView() {
        binding.inventarioRecyclerView.layoutManager = LinearLayoutManager(this)
        adapterInvViewHolder = InventRecyclerViewAdapter({selectedItem: Inventario->listItemClicked(selectedItem)})
        binding.inventarioRecyclerView.adapter = adapterInvViewHolder
        displayInvList()
    }

    fun displayInvList() {
        inventarioViewModel.inventarios.observe(this, Observer {
            adapterInvViewHolder.setList(it)
            adapterInvViewHolder.notifyDataSetChanged()
        })
    }

    fun listItemClicked(inventario: Inventario) {
        inventario
        inventarioViewModel.initUpadetAndDelete(inventario)
    }

    fun loadSpinner(){
        //spinnerLoadUnid()
        var myStrings = arrayOf("011 - kg", "001 - litro", "002 - unidade", "017 - caixa")
        spinnerLoad(myStrings, R.id.spinnerUnid)

        //spinnerLoadEnd1()
        myStrings = arrayOf("019 - Rua 19", "030 - Rua Principal", "002 - Rua da Prata")
        spinnerLoad(myStrings, R.id.spinnerEnd1)

        //spinnerLoadEnd2()
        myStrings = arrayOf("001 - Coluna 1", "030 - Coluna 30", "002 - Segunda coluna")
        spinnerLoad(myStrings, R.id.spinnerEnd2)

        //spinnerLoadEnd3()
        myStrings = arrayOf("001 - Prateleira 1010", "020 - Prateleira 1121", "054 - Prateleira 89")
        spinnerLoad(myStrings, R.id.spinnerEnd3)

        //spinnerLoadEnd4()
        myStrings = arrayOf("089 - Palete 89", "099 - Palete 99", "100 - Palete 100")
        spinnerLoad(myStrings, R.id.spinnerEnd4)

        //spinnerLoadEnd5()
        myStrings = arrayOf("550 - Cx. 550", "867 - Cx. 867", "300 - Cx. 300", "089 - Cx. 089")
        spinnerLoad(myStrings, R.id.spinnerEnd5)

        //spinnerLoadEnd6()
        myStrings = arrayOf("005 - Cabide 5", "020 - Cabide 20", "054 - Cabide 54")
        spinnerLoad(myStrings, R.id.spinnerEnd6)

        // spinnerLoadLocal()
        myStrings = arrayOf("001 - Matriz", "010 - Filial 10", "015 - Filial 15")
        spinnerLoad(myStrings, R.id.spinnerLocal)
    }

    fun spinnerLoad(arrayInf: Array<String>, spinner: Int) {
        //fonte de dados
        val myStrings = arrayInf
        //Adapter for spinner
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, myStrings)
        val spinner: Spinner = findViewById(spinner)

        spinner.adapter = arrayAdapter
    }


}