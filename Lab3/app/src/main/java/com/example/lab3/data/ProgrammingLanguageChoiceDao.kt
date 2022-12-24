package com.example.lab3.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProgrammingLanguageChoiceDao {
    @Insert
    suspend fun insert(entity: ProgrammingLanguageChoice)

    @Query("SELECT * FROM programming_language_choice")
    suspend fun getAll(): List<ProgrammingLanguageChoice>
}
