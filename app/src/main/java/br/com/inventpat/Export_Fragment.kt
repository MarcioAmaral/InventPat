package br.com.inventpat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer

class Export_Fragment : Fragment() {

    companion object {
        fun newInstance() = Export_Fragment()
    }

    private lateinit var exportViewModel: ExportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exportViewModel = ViewModelProvider(this).get(ExportViewModel::class.java)
        val root = inflater.inflate(R.layout.export_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_export)
        exportViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        exportViewModel = ViewModelProvider(this).get(ExportViewModel::class.java)
        // TODO: Use the ViewModel
    }

}