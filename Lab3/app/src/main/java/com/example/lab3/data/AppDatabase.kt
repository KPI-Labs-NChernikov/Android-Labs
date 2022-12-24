package com.example.lab3.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lab3.data.helperClasses.LocalDateTimeConverter

@Database(entities = [ProgrammingLanguageChoice::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun programmingLanguageChoiceDao(): ProgrammingLanguageChoiceDao

    companion object {
        const val NAME = "app.db"
    }
}
