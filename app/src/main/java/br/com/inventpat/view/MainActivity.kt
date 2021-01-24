package br.com.inventpat.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.inventpat.R
import br.com.inventpat.databinding.ActivityMainBinding
import br.com.inventpat.model.inventario.Inventario
import br.com.inventpat.model.inventario.InventarioDatabase
import br.com.inventpat.model.inventario.InventarioRepository
import br.com.inventpat.viewmodel.InventarioViewModelFactory
import br.com.invpatrim.viewmodel.InventarioViewModel
import com.google.android.material.navigation.NavigationView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var inventarioViewModel: InventarioViewModel
    private lateinit var adapterInvViewHolder: InventRecyclerViewAdapter
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var toggle: ActionBarDrawerToggle

   // private val CAMREQUEST = 1
    private val PICK_IMAGE_CODE = 1
  //  private val PICK_IMAGE = 1
  //  var fileUri: Uri? = null
    var currentPhotoPath = ""
    lateinit var bitFotoSelecionada: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_export -> Toast.makeText(applicationContext,
                "Clicked Item 2", Toast.LENGTH_LONG).show()

                R.id.nav_import -> Toast.makeText(applicationContext,
                    "Clicked Item 2", Toast.LENGTH_LONG).show()
            }
         //   toolbar.setNavigationIcon(R.drawable.ic_lock)  // Trocar icon da drawer
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        /*val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_export, R.id.nav_import), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/
// Fragment
//        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //test
        /*val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController*/

        /*appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_export, R.id.nav_import), drawerLayout)*/
        //test
       // setupActionBarWithNavController(navController, appBarConfiguration)
       // navView.setupWithNavController(navController)

        val dao = InventarioDatabase.getInstance(application).inventarioDao
        val repository = InventarioRepository(dao)
        val factory = InventarioViewModelFactory(repository)
        inventarioViewModel = ViewModelProvider(this, factory).get(InventarioViewModel::class.java)
        binding.viewModel = inventarioViewModel
        binding.lifecycleOwner = this

        initRecyclerView()

        inventarioViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let { it ->
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        loadSpinner()

        binding.spinnerLocal.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    inventarioViewModel.spLocal.value = parent.getItemAtPosition(position).toString()
                    
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.spinnerUnid.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    inventarioViewModel.spUnidMed.value = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.spinnerEnd1.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    inventarioViewModel.spEnd1.value = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.spinnerEnd2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    inventarioViewModel.spEnd2.value = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.spinnerEnd3.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    inventarioViewModel.spEnd3.value = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.spinnerEnd4.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    inventarioViewModel.spEnd4.value = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.spinnerEnd5.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    inventarioViewModel.spEnd5.value = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        binding.spinnerEnd6.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    inventarioViewModel.spEnd6.value = parent.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        inventarioViewModel.spLocal.observe(this, Observer {
            if (it.isNullOrBlank()) {
                clearSpinner()
            }
        })

        inventarioViewModel.inputCodigo.observe(this, Observer {
            if (!it.isNullOrBlank() && inventarioViewModel.btnIncluir.value == true) {
                var spinnerKey =
                    adapterInvViewHolder.itemLocal.toString().substringBefore("-").trim()
                var posicao = getIndexByString(binding.spinnerLocal, spinnerKey)
                binding.spinnerLocal.setSelection(posicao)

                posicao = getIndexByString(binding.spinnerUnid, spinnerKey)
                binding.spinnerUnid.setSelection(posicao)

                spinnerKey = adapterInvViewHolder.itemEnd1.toString().substringBefore("-").trim()
                posicao = getIndexByString(binding.spinnerEnd1, spinnerKey)
                binding.spinnerEnd1.setSelection(posicao)

                spinnerKey = adapterInvViewHolder.itemEnd2.toString().substringBefore("-").trim()
                posicao = getIndexByString(binding.spinnerEnd2, spinnerKey)
                binding.spinnerEnd2.setSelection(posicao)

                spinnerKey = adapterInvViewHolder.itemEnd3.toString().substringBefore("-").trim()
                posicao = getIndexByString(binding.spinnerEnd3, spinnerKey)
                binding.spinnerEnd3.setSelection(posicao)

                spinnerKey = adapterInvViewHolder.itemEnd4.toString().substringBefore("-").trim()
                posicao = getIndexByString(binding.spinnerEnd4, spinnerKey)
                binding.spinnerEnd4.setSelection(posicao)

                spinnerKey = adapterInvViewHolder.itemEnd5.toString().substringBefore("-").trim()
                posicao = getIndexByString(binding.spinnerEnd5, spinnerKey)
                binding.spinnerEnd5.setSelection(posicao)

                spinnerKey = adapterInvViewHolder.itemEnd6.toString().substringBefore("-").trim()
                posicao = getIndexByString(binding.spinnerEnd6, spinnerKey)
                binding.spinnerEnd6.setSelection(posicao)

            }
        })
        binding.imageView.setOnClickListener {
            Toast.makeText(
                this,
                "Abrir foto  ${inventarioViewModel.inputFoto.value}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getIndexByString(spinner: Spinner, keySpinner: String): Int {
        var index = 0
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().substringBefore("-").trim().equals(
                    keySpinner,
                    ignoreCase = true
                )) {
                index = i
                break
            }
        }
        return index
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }*/

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }*/
    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Toast.makeText(this, "Click menu", Toast.LENGTH_LONG).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }*/

    private fun clearSpinner() {
        binding.spinnerLocal.setSelection(0)
        binding.spinnerUnid.setSelection(0)
        binding.spinnerEnd1.setSelection(0)
        binding.spinnerEnd2.setSelection(0)
        binding.spinnerEnd3.setSelection(0)
        binding.spinnerEnd4.setSelection(0)
        binding.spinnerEnd5.setSelection(0)
        binding.spinnerEnd6.setSelection(0)
        binding.spinnerLocal.requestFocus(0)

    }

    fun initRecyclerView() {
        binding.inventarioRecyclerView.layoutManager = LinearLayoutManager(this)
        adapterInvViewHolder = InventRecyclerViewAdapter { selectedItem: Inventario ->
            listItemClicked(
                selectedItem
            )
        }
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
        //inventario
        inventarioViewModel.initUpadetAndDelete(inventario)
    }

    fun loadSpinner(){
        //spinnerLoadUnid()
        var myStrings = arrayOf(
            "Selecionar",
            "011 - kg",
            "001 - litro",
            "002 - unidade",
            "017 - caixa"
        )
        spinnerLoad(myStrings, R.id.spinnerUnid)

        //spinnerLoadEnd1()
        myStrings = arrayOf(
            "Selecionar",
            "019 - Rua 19",
            "030 - Rua Principal",
            "002 - Rua da Prata"
        )
        spinnerLoad(myStrings, R.id.spinnerEnd1)

        //spinnerLoadEnd2()
        myStrings = arrayOf(
            "Selecionar",
            "001 - Coluna 1",
            "030 - Coluna 30",
            "002 - Segunda coluna"
        )
        spinnerLoad(myStrings, R.id.spinnerEnd2)

        //spinnerLoadEnd3()
        myStrings = arrayOf(
            "Selecionar",
            "001 - Prateleira 1010",
            "020 - Prateleira 1121",
            "054 - Prateleira 89"
        )
        spinnerLoad(myStrings, R.id.spinnerEnd3)

        //spinnerLoadEnd4()
        myStrings = arrayOf("Selecionar", "089 - Palete 89", "099 - Palete 99", "100 - Palete 100")
        spinnerLoad(myStrings, R.id.spinnerEnd4)

        //spinnerLoadEnd5()
        myStrings = arrayOf(
            "Selecionar",
            "550 - Cx. 550",
            "867 - Cx. 867",
            "300 - Cx. 300",
            "089 - Cx. 089"
        )
        spinnerLoad(myStrings, R.id.spinnerEnd5)

        //spinnerLoadEnd6()
        myStrings = arrayOf("Selecionar", "005 - Cabide 5", "020 - Cabide 20", "054 - Cabide 54")
        spinnerLoad(myStrings, R.id.spinnerEnd6)

        // spinnerLoadLocal()
        myStrings = arrayOf(
            "Selecionar o Local",
            "001 - Matriz",
            "010 - Filial 10",
            "015 - Filial 15"
        )
        spinnerLoad(myStrings, R.id.spinnerLocal)
    }

    fun spinnerLoad(arrayInf: Array<String>, spinner: Int) {
        //fonte de dados
        //Adapter for spinner
        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayInf
        )
        val spinner: Spinner = findViewById(spinner)

        spinner.adapter = arrayAdapter
    }

    fun abrirCamera(view: View) {   // Camera
        if (binding.idCodigo.text.isNullOrBlank()) {
            Toast.makeText(
                this,
                "Por favor informe o código do item antes da foto",
                Toast.LENGTH_SHORT
            ).show()
            binding.idCodigo.requestFocus(0)
            return
        }

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    photoFile.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "br.com.inventpat",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, PICK_IMAGE_CODE)
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("ddMMyyy_HHmmss").format(Date())
        val storageDir: File? = createPicturesPath("InventPat")

        return File.createTempFile(
            "IMG${binding.idCodigo.text}_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun createPicturesPath(directory: String): File? {
        var mediaStorageDir: File? = null
        try {
            mediaStorageDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), directory)
            // Cria a pasta se não existir
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()){
                    Toast.makeText(this, "", Toast.LENGTH_LONG).show()
                    return null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return mediaStorageDir
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            when(requestCode){
                PICK_IMAGE_CODE ->
                    if (resultCode == Activity.RESULT_OK) {
                        //val thumbnail = MediaStore.Images.Media.getBitmap(
                        //contentResolver, imageUri)
                        //  bitFotoSelecionada = data.extras?.get("data") as Bitmap
                        // colocaFotoProduto.setImageBitmap(bitFotoSelecionada)

                        val file = File(currentPhotoPath)
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            contentResolver, Uri.fromFile(
                                file
                            )
                        )
                        bitFotoSelecionada = bitmap
                        inventarioViewModel.inputFoto.value = Uri.fromFile(file).toString()
                        /*val imageUrl = Uri.fromFile(file).toString()
                        if (!imageUrl.isNullOrEmpty()) {
                            Glide.with(this)
                                .load(imageUrl)
                                .into(binding.imageView)
                        }*/
                    }
            }
        }catch (e: Exception){
            e.printStackTrace()

        }
    }

    /*@BindingAdapter("imageFromUrl")
    fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }*/
}