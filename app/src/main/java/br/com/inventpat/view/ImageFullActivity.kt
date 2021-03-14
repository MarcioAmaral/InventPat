package br.com.inventpat.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.inventpat.R
import br.com.inventpat.databinding.ActivityImageFullBinding
import br.com.inventpat.model.inventario.InventarioDatabase
import br.com.inventpat.model.inventario.InventarioRepository
import br.com.inventpat.util.GlideApp
import br.com.inventpat.viewmodel.InventarioViewModelFactory
import br.com.invpatrim.viewmodel.InventarioViewModel

class ImageFullActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageFullBinding
    private lateinit var inventarioViewModel: InventarioViewModel<Any?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_full)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val dao = InventarioDatabase.getInstance(application).inventarioDao
        val repository = InventarioRepository(dao)
        val factory = InventarioViewModelFactory(repository)
        inventarioViewModel = ViewModelProvider(this, factory).get(InventarioViewModel::class.java) as InventarioViewModel<Any?>

        binding.viewModel = inventarioViewModel
        binding.lifecycleOwner = this

        inventarioViewModel.inputCodigo.value = intent.getStringExtra("codigo")
        inventarioViewModel.inputDescricao.value = intent.getStringExtra("descricao")
      //  inventarioViewModel.inputFoto.value = intent.getStringExtra("foto")
        val currentPhotoPath = intent.getStringExtra("foto")
        if (!currentPhotoPath.isNullOrEmpty()) {
            GlideApp.with(this)
                .load(currentPhotoPath)
                .into(binding.imageView)
        }

        toolbar.setNavigationOnClickListener {
            // setSupportActionBar(toolbar)
            finish()
        }
    }
}