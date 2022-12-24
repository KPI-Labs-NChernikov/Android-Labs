package com.example.lab3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.example.lab3.data.ProgrammingLanguageChoice
import com.example.lab3.data.AppDatabase
import com.example.lab3.interfaces.CancelledListener
import com.example.lab3.interfaces.HistoryOpenedListener
import com.example.lab3.interfaces.LanguageSelectedListener
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class MainActivity : LanguageSelectedListener, CancelledListener, HistoryOpenedListener,
    AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ChoiceFragment>(R.id.fragment_container_view)
            }
        }
    }

    override fun onSelected(language: String) {
        showResult(language)
        writeResultToDb(language)
    }

    private fun showResult(resultLanguage: String)
    {
        val fragment = ResultFragment()
        val bundle = bundleOf("selected" to resultLanguage)
        fragment.arguments = bundle
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, fragment)
            addToBackStack(null)
        }
    }

    private fun writeResultToDb(resultLanguage: String)
    {
        val db = AppDatabase.create(applicationContext)
        val dao = db.programmingLanguageChoiceDao()
        lifecycleScope.launch {
            dao.insert(ProgrammingLanguageChoice.create(resultLanguage, LocalDateTime.now()))
            val toastText = "Your choice ($resultLanguage programming language) was saved successfully"
            val toast = Toast.makeText(applicationContext, toastText, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    override fun onCancelled() {
        supportFragmentManager.popBackStack()
    }

    override fun onOpened() {
        val intent = Intent(applicationContext, HistoryActivity::class.java)
        startActivity(intent)
    }
}
