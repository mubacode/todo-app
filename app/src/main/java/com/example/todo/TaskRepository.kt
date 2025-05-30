package com.example.todo

import android.content.Context
import com.example.todo.data.FirestoreRepository
import com.example.todo.data.FirestoreTask
import com.example.todo.data.Task
import com.example.todo.util.InstallationIdentifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TaskRepository(
    private val taskDao: TaskDao,
    private val context: Context,
    private val firestoreRepository: FirestoreRepository = FirestoreRepository()
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val installationId = InstallationIdentifier.getInstallationId(context)

    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insertTask(task: Task) {
        // Add installation ID to the task
        val taskWithInstallationId = task.copy(installationId = installationId)
        
        // Save locally
        val localId = taskDao.insertTask(taskWithInstallationId)
        
        // Save to Firestore
        try {
            val taskWithId = taskWithInstallationId.copy(id = localId)
            firestoreRepository.addTask(FirestoreTask.fromTask(taskWithId))
        } catch (e: Exception) {
            // Handle error (maybe retry later)
        }
    }

    suspend fun updateTask(task: Task) {
        // Update locally
        taskDao.updateTask(task)
        
        // Update in Firestore
        try {
            firestoreRepository.updateTask(FirestoreTask.fromTask(task))
        } catch (e: Exception) {
            // Handle error
        }
    }

    suspend fun deleteTask(task: Task) {
        // Delete locally
        taskDao.deleteTask(task)
        
        // Delete from Firestore
        try {
            firestoreRepository.deleteTask(FirestoreTask.fromTask(task))
        } catch (e: Exception) {
            // Handle error
        }
    }

    fun syncWithCloud() {
        coroutineScope.launch {
            try {
                // Get all local tasks
                val localTasks = getAllTasks().first()
                
                // Sync with Firestore
                firestoreRepository.syncTasks(localTasks, installationId)
                
                // Get updated tasks from Firestore
                firestoreRepository.getTasks(installationId).first().forEach { firestoreTask ->
                    val localTask = firestoreTask.toTask()
                    taskDao.insertTask(localTask)
                }
            } catch (e: Exception) {
                // Handle sync error
            }
        }
    }
} 