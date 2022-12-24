package com.example.lab3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lab3.data.helperClasses.LocalDateTimeConverter

@Database(entities = [ProgrammingLanguageChoice::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun programmingLanguageChoiceDao(): ProgrammingLanguageChoiceDao

    companion object {
        private const val NAME = "app.db"

        fun create(context: Context): AppDatabase
        {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, NAME
            ).build()
        }
    }
}
