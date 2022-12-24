package com.example.lab3.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "programming_language_choice")
data class ProgrammingLanguageChoice(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    @ColumnInfo(name = "created_at") val createdAt: LocalDateTime
)
{
    companion object
    {
        fun create(name: String, createdAt: LocalDateTime) : ProgrammingLanguageChoice
        {
            return ProgrammingLanguageChoice(0, name, createdAt)
        }
    }
}
