package com.example.lab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.lab3.data.AppDatabase
import com.example.lab3.data.ProgrammingLanguageChoice
import com.example.lab3.data.ProgrammingLanguageChoiceDao
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HistoryActivity : AppCompatActivity(R.layout.activity_history) {
    override fun onCreate(savedInstanceState: Bundle?) {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, AppDatabase.NAME
        ).build()

        Log.d("hello", "Hello world message")
        var l: List<ProgrammingLanguageChoice>
        var dao: ProgrammingLanguageChoiceDao
        lifecycleScope.launch {
            Log.d("hello", "Hello from lifecycleScope")
            dao = db.programmingLanguageChoiceDao()
            dao.insert(ProgrammingLanguageChoice
                (0, "dasasadsaxa", LocalDateTime.now()))
            l = dao.getAll()
            Log.d("hello", l.last().createdAt.toString() + " is a date")
            Log.d("hello", l.size.toString())
            Log.d("hello", "Hello from lifecycleScope #2")
        }
        super.onCreate(savedInstanceState)
    }
}
