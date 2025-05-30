package com.example.todo.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val tasksCollection = firestore.collection("tasks")

    fun getTasks(installationId: String): Flow<List<FirestoreTask>> = callbackFlow {
        val subscription = tasksCollection
            .whereEqualTo("installationId", installationId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // Handle error
                    return@addSnapshotListener
                }

                val tasks = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(FirestoreTask::class.java)
                } ?: emptyList()
                
                trySend(tasks)
            }

        awaitClose { subscription.remove() }
    }

    suspend fun addTask(task: FirestoreTask) {
        tasksCollection.add(task).await()
    }

    suspend fun updateTask(task: FirestoreTask) {
        tasksCollection.document(task.documentId).set(task).await()
    }

    suspend fun deleteTask(task: FirestoreTask) {
        tasksCollection.document(task.documentId).delete().await()
    }

    suspend fun syncTasks(localTasks: List<Task>, installationId: String) {
        val remoteTasks = tasksCollection
            .whereEqualTo("installationId", installationId)
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(FirestoreTask::class.java) }

        // Convert local tasks to a map for easy lookup
        val localTaskMap = localTasks.associateBy { it.id.toString() }
        val remoteTaskMap = remoteTasks.associateBy { it.documentId }

        // Add new local tasks to Firestore
        localTasks.filter { !remoteTaskMap.containsKey(it.id.toString()) }
            .forEach { addTask(FirestoreTask.fromTask(it)) }

        // Update existing tasks in Firestore
        localTasks.filter { remoteTaskMap.containsKey(it.id.toString()) }
            .forEach { updateTask(FirestoreTask.fromTask(it)) }

        // Delete tasks from Firestore that don't exist locally
        remoteTasks.filter { !localTaskMap.containsKey(it.documentId) }
            .forEach { deleteTask(it) }
    }
} 