package com.example.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val dueDate: Date? = null,
    val reminderTime: Date? = null,
    val priority: Priority = Priority.MEDIUM,
    val category: Category = Category.OTHER,
    val isCompleted: Boolean = false,
    val installationId: String = ""
) 