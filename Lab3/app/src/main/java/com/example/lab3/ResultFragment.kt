package com.example.lab3

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.lab3.interfaces.CancelledListener

class ResultFragment : Fragment() {
    private lateinit var selected: String
    private lateinit var cancelledListener: CancelledListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            cancelledListener = context as CancelledListener
        } catch (_: ClassCastException) {
            throw ClassCastException("$context must implement ${CancelledListener::class.simpleName}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)
        setUpSelectedVisualization(view)
        setUpButtons(view)
        return view
    }

    private fun setUpSelectedVisualization(view: View) {
        selected = requireArguments().getString("selected")
            ?: throw IllegalArgumentException("Provide an argument for a \"selected\" key")
        view.findViewById<TextView>(R.id.result_language).text = selected
    }

    private fun setUpButtons(view: View) {
        val cancelButton = view.findViewById<Button>(R.id.cancel_button)
        cancelButton.setOnClickListener {
            cancelledListener.onCancelled()
        }
    }
}
