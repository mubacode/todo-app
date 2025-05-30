package com.example.todo.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.Date

data class FirestoreTask(
    @DocumentId
    val documentId: String = "",
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: String = "MEDIUM",
    val category: String = "OTHER",
    val dueDate: Timestamp? = null,
    val reminderTime: Timestamp? = null,
    val installationId: String = ""
) {
    fun toTask(id: Long = 0): Task {
        return Task(
            id = id,
            title = title,
            description = description,
            isCompleted = isCompleted,
            priority = Priority.valueOf(priority),
            category = Category.valueOf(category),
            dueDate = dueDate?.toDate(),
            reminderTime = reminderTime?.toDate(),
            installationId = installationId
        )
    }

    companion object {
        fun fromTask(task: Task): FirestoreTask {
            return FirestoreTask(
                documentId = task.id.toString(),
                title = task.title,
                description = task.description,
                isCompleted = task.isCompleted,
                priority = task.priority.name,
                category = task.category.name,
                dueDate = task.dueDate?.let { Timestamp(it) },
                reminderTime = task.reminderTime?.let { Timestamp(it) },
                installationId = task.installationId
            )
        }
    }
} 