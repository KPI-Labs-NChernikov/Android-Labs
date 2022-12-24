package com.example.lab3

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.lab3.data.AppDatabase
import com.example.lab3.data.ProgrammingLanguageChoice
import com.example.lab3.data.ProgrammingLanguageChoiceDao
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class HistoryActivity : AppCompatActivity(R.layout.activity_history) {
    private lateinit var dao: ProgrammingLanguageChoiceDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpButtons()
        initializeDao()
        lifecycleScope.launch {
            val languagesHistory = dao.getAll()
            showLanguagesHistory(languagesHistory)
        }
    }

    private fun setUpButtons()
    {
        val toMainButton = findViewById<Button>(R.id.to_main_button)
        toMainButton.setOnClickListener{
            finish()
        }
    }

    private fun initializeDao()
    {
        val db = AppDatabase.create(applicationContext)
        dao = db.programmingLanguageChoiceDao()
    }

    private fun showLanguagesHistory(languagesHistory: List<ProgrammingLanguageChoice>)
    {
        if (languagesHistory.isEmpty())
        {
            showNoItemsMessage()
            return
        }

        showLanguagesHistoryItems(languagesHistory)
    }

    private fun showNoItemsMessage()
    {
        val loadingView = findViewById<TextView>(R.id.history_languages_list_loading)
        val noItemsView = findViewById<TextView>(R.id.history_languages_list_no_items)
        loadingView.visibility = View.GONE
        noItemsView.visibility = View.VISIBLE
    }

    private fun showLanguagesHistoryItems(languagesHistory: List<ProgrammingLanguageChoice>)
    {
        val loadingView = findViewById<TextView>(R.id.history_languages_list_loading)
        val listView = findViewById<ListView>(R.id.history_languages_list)
        val clearButtonWrapper = findViewById<LinearLayout>(R.id.clear_button_wrapper)

        setUpLanguagesHistoryView(languagesHistory)
        setUpLanguagesHistoryViewButtons()

        loadingView.visibility = View.GONE
        listView.visibility = View.VISIBLE
        clearButtonWrapper.visibility = View.VISIBLE
    }

    private fun setUpLanguagesHistoryView(languagesHistory: List<ProgrammingLanguageChoice>)
    {
        val listView = findViewById<ListView>(R.id.history_languages_list)

        val mappedHistory = languagesHistory.map { item ->
            val formattedCreatedAt = item.createdAt
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
            "${item.name} (${formattedCreatedAt})"
        }

        val adapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_list_item_1,
            mappedHistory
        )

        listView.adapter = adapter
    }

    private fun setUpLanguagesHistoryViewButtons()
    {
        val clearButtonView = findViewById<Button>(R.id.clear_button)
        clearButtonView.setOnClickListener{
            lifecycleScope.launch {
                dao.clear()
                val toastText = "The history was cleared successfully"
                val toast = Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT)
                toast.show()
                finish()
            }
        }
    }
}
