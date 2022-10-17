package com.example.lab2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.lab2.interfaces.CancelledListener
import com.example.lab2.interfaces.LanguageSelectedListener

class MainActivity : LanguageSelectedListener, CancelledListener, AppCompatActivity(R.layout.activity_main) {
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
        }
    }

    override fun onCancelled() {
        supportFragmentManager.commit {
            replace<ChoiceFragment>(R.id.fragment_container_view)
        }
    }
}
