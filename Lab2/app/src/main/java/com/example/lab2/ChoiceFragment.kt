package com.example.lab2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.core.view.children
import com.example.lab2.interfaces.LanguageSelectedListener

class ChoiceFragment : Fragment() {
    private lateinit var languageSelectedListener: LanguageSelectedListener

    private lateinit var programmingLanguages: Array<String>
    private val groupName = "Programming languages"
    private lateinit var programmingLanguagesGroups: HashMap<String, Array<String>>

    private lateinit var programmingLanguagesListView: ExpandableListView
    private lateinit var adapter: CustomExpandableListAdapter

    private var _selected: ExpandableListViewItem? = null
    private var selected: ExpandableListViewItem?
        get() {
            return _selected
        }
        set(value) {
            if (selected?.view != null) {
                if (!viewReinitialized) makeTransparent(selected!!.view)
                else for (view in programmingLanguagesListView.children) makeTransparent(view)
            }
            if (value != null) highlight(value.view)
            adapter.selectedPosition = value?.position
            viewReinitialized = false
            _selected = value
        }

    private var viewReinitialized = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choice, container, false)
        _selected = null
        seedHashMap()
        setUpListView(view)
        setUpButtons(view)
        return view
    }

    private fun seedHashMap() {
        programmingLanguages = resources.getStringArray(R.array.programming_languages)
        programmingLanguagesGroups = HashMap()
        programmingLanguagesGroups[groupName] = programmingLanguages
    }

    private fun setUpListView(view: View) {
        programmingLanguagesListView = view.findViewById(R.id.programming_languages)
        adapter = CustomExpandableListAdapter(
            context, arrayOf(groupName), programmingLanguagesGroups
        )
        programmingLanguagesListView.setAdapter(adapter)
        programmingLanguagesListView.setOnChildClickListener { _, currentView, groupIndex, childIndex, _ ->
            selected = ExpandableListViewItem(
                currentView, ExpandableListViewItemPosition(groupIndex, childIndex)
            )
            true
        }
        programmingLanguagesListView.setOnGroupCollapseListener {
            viewReinitialized = true
        }
    }

    private fun setUpButtons(view: View) {
        val okButton = view.findViewById<Button>(R.id.ok_button)
        okButton.setOnClickListener {
            viewResult()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            languageSelectedListener = context as LanguageSelectedListener
        } catch (_: ClassCastException) {
            throw ClassCastException("$context must implement ${LanguageSelectedListener::class.simpleName}")
        }
    }

    private fun viewResult() {
        if (selected == null) {
            val toastText = "Please, select the programming language first"
            val toast = Toast.makeText(context, toastText, Toast.LENGTH_SHORT)
            toast.show()
            return
        }
        val selectedPosition = selected!!.position
        val selectedValue =
            adapter.getChild(selectedPosition.groupIndex, selectedPosition.childIndex).toString()
        languageSelectedListener.onSelected(selectedValue)
    }
}
