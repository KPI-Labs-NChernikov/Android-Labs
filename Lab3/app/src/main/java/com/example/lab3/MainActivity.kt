package com.example.lab3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.lab3.interfaces.CancelledListener
import com.example.lab3.interfaces.HistoryOpenedListener
import com.example.lab3.interfaces.LanguageSelectedListener

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
        val fragment = ResultFragment()
        val bundle = bundleOf("selected" to language)
        fragment.arguments = bundle
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, fragment)
            addToBackStack(null)
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
