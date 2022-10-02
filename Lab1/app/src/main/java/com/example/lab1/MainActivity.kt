package com.example.lab1

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private val programmingLanguages = arrayOf("C", "C++", "C#", "Java", "Kotlin",
        "JavaScript", "TypeScript", "Ruby", "Rust")
    private val groupName = "Programming languages"
    private lateinit var programmingLanguagesGroups : HashMap<String, Array<String>>

    private lateinit var programmingLanguagesListView : ExpandableListView
    private lateinit var adapter : CustomExpandableListAdapter

    private var _selected: ExpandableListViewItem? = null
    private var selected: ExpandableListViewItem?
    get()
    {
        return _selected
    }
    set(value)
    {
        if (selected?.view != null) {
            if (!viewReinitialized)
                makeTransparent(selected!!.view)
            else
                for (view in programmingLanguagesListView.children)
                    makeTransparent(view)
        }
        if (value != null)
            highlight(value.view)
        adapter.selectedPosition = value?.position
        viewReinitialized = false
        _selected = value
    }

    private var viewReinitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seedHashMap()
        setUpListView()
        setUpButtons()
    }

    private fun seedHashMap()
    {
        programmingLanguagesGroups = HashMap()
        programmingLanguagesGroups[groupName] = programmingLanguages
    }

    private fun setUpListView()
    {
        programmingLanguagesListView = findViewById(R.id.programming_languages)
        adapter = CustomExpandableListAdapter(this, arrayOf(groupName),
            programmingLanguagesGroups)
        programmingLanguagesListView.setAdapter(adapter)
        programmingLanguagesListView.setOnChildClickListener { _, view, groupIndex, childIndex, _ ->
            selected = ExpandableListViewItem(view,
                ExpandableListViewItemPosition(groupIndex, childIndex))
            true
        }
        programmingLanguagesListView.setOnGroupCollapseListener {
            viewReinitialized = true
        }
    }

    private fun setUpButtons()
    {
        val okButton = findViewById<Button>(R.id.ok_button)
        okButton.setOnClickListener {
            onOkClicked()
        }

        val cancelButton = findViewById<Button>(R.id.cancel_button)
        cancelButton.setOnClickListener {
            onCancelClicked()
        }
    }

    private fun onOkClicked()
    {
        viewResult()
    }

    private fun viewResult()
    {
        if (selected == null)
        {
            val toastText = "Please, select the programming language first"
            val toast = Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT)
            toast.show()
            return
        }
        val resultView = findViewById<TextView>(R.id.result_message)
        val selectedPosition = selected!!.position
        val selectedValue = adapter.getChild(selectedPosition.groupIndex, selectedPosition.childIndex).toString()
        resultView.text = getString(R.string.result_message, selectedValue)
        resultView.visibility = View.VISIBLE
    }

    private fun onCancelClicked()
    {
        resetResult()
    }

    private fun resetResult()
    {
        val resultView = findViewById<TextView>(R.id.result_message)
        resultView.visibility = View.GONE
        selected = null
    }
}
