package br.com.inventpat.view

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.inventpat.R
import br.com.inventpat.model.inventario.InventarioDatabase
import br.com.inventpat.model.inventario.InventarioRepository
import br.com.inventpat.viewmodel.InventarioViewModelFactory
import br.com.invpatrim.viewmodel.InventarioViewModel
import com.ajts.androidmads.library.ExcelToSQLite
import com.ajts.androidmads.library.SQLiteToExcel
import kotlinx.coroutines.launch
import java.io.File


class DrawerActivity : AppCompatActivity() {
    private lateinit var inventarioViewModel: InventarioViewModel<Any?>
    private lateinit var btnExport: Button
    private lateinit var btnImport: Button
    private lateinit var btnClearAll: Button
    private lateinit var edtTxtPath: EditText
    private lateinit var progressBar: ProgressBar
    lateinit var sqlLiteToExcel: SQLiteToExcel
    val directoryPath = Environment.getExternalStorageDirectory().path!!

    val directoryPathImp = "$directoryPath/inventario.xls"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)

        progressBar = findViewById(R.id.progressBar)
        btnExport = findViewById(R.id.btnExport)
        btnImport = findViewById(R.id.btnImport)
        btnClearAll = findViewById(R.id.btnClearAll)
        edtTxtPath = findViewById(R.id.edtTxtPath)
        val dao = InventarioDatabase.getInstance(application).inventarioDao
        val repository = InventarioRepository(dao)
        val factory = InventarioViewModelFactory(repository)
        inventarioViewModel = ViewModelProvider(this, factory).get(InventarioViewModel::class.java) as InventarioViewModel<Any?>

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (ActivityCompat.checkSelfPermission(
                    this@DrawerActivity,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            )
            else
                ActivityCompat.requestPermissions(
                    this@DrawerActivity,
                    arrayOf<String>(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
                )
        else
            Toast.makeText(this, "", Toast.LENGTH_LONG).show()

        when (intent.getStringExtra("opcao")) {
            "export" -> btnExport.isEnabled = true

            "import" -> {
                btnImport.isEnabled = true
                edtTxtPath.isEnabled = true
                edtTxtPath.setText(directoryPathImp)
            }

            "clearAll" -> btnClearAll.isEnabled = true
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
        btnExport.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                // Export SQLite DB as EXCEL FILE
                sqlLiteToExcel = SQLiteToExcel(
                    applicationContext,
                    InventarioDatabase.BASE_NOME,
                    directoryPath
                )
                progressBar.visibility = View.VISIBLE
                sqlLiteToExcel.exportAllTables(
                    "inventario.xls",
                    object : SQLiteToExcel.ExportListener {
                        override fun onStart() {
                        }

                        override fun onCompleted(filePath: String) {
                            Toast.makeText(
                                this@DrawerActivity,
                                "Exportado com sucesso! Tabela: inventario.xls",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }

                        override fun onError(e: Exception) {
                            progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@DrawerActivity,
                                "${e.message.toString()}",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }
                    })
            }
        })

        btnImport.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val file = File(directoryPathImp)
                if (!file.exists()) {
                    Toast.makeText(this@DrawerActivity, "Arquivo não localizado", Toast.LENGTH_LONG)
                        .show()
                    return
                }
                progressBar.visibility = View.VISIBLE
                val excelToSQLite = ExcelToSQLite(
                    applicationContext,
                    InventarioDatabase.BASE_NOME,
                    true
                )
                excelToSQLite.importFromFile(
                    directoryPathImp,
                    object : ExcelToSQLite.ImportListener {
                        override fun onStart() {
                        }

                        override fun onCompleted(dbName: String?) {
                            Toast.makeText(
                                this@DrawerActivity,
                                "Importação com sucesso!",
                                Toast.LENGTH_LONG
                            ).show()
                            inventarioViewModel.controlarBD(true)
                            finish()
                        }

                        override fun onError(e: Exception) {
                            progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@DrawerActivity,
                                e.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }
                    })
            }
        })

        btnClearAll.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                withStyle()
            }
        })
    }

    fun withStyle() {

        val builder = AlertDialog.Builder(
            ContextThemeWrapper(
                this,
                android.R.style.Holo_SegmentedButton
            )
        )

        with(builder)
        {
            setTitle("Confirma operação?")
            setMessage("Atenção, você está prestes a excluir todos os registros da base Inventário. Caso confirmado O aplicativo vai ser fechado. Favor abri-lo novamente")
            setPositiveButton("OK", DialogInterface.OnClickListener(function = positiveButtonClick))
            setNegativeButton(android.R.string.cancel, negativeButtonClick)
            show()
        }
    }

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            inventarioViewModel.limparInventario()
        }
        progressBar.visibility = View.GONE
        Toast.makeText(
            applicationContext,
            "Base restaurada com sucesso!", Toast.LENGTH_SHORT
        ).show()
        inventarioViewModel.controlarBD(true)
        finish()
    }

    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(
            applicationContext,
            android.R.string.cancel, Toast.LENGTH_SHORT
        ).show()
        finish()
    }
}