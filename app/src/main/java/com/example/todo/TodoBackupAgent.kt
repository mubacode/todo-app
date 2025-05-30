package com.example.todo

import android.app.backup.BackupAgent
import android.app.backup.BackupDataInput
import android.app.backup.BackupDataOutput
import android.os.ParcelFileDescriptor
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream

class TodoBackupAgent : BackupAgent() {
    companion object {
        private const val TASKS_BACKUP_KEY = "tasks_backup"
    }

    private val installationId: String
        get() = (applicationContext as TodoApplication).installationId

    override fun onBackup(
        oldState: ParcelFileDescriptor?,
        data: BackupDataOutput,
        newState: ParcelFileDescriptor
    ) {
        // Backup Firestore data
        runBlocking {
            try {
                val db = FirebaseFirestore.getInstance()
                val tasks = db.collection("tasks")
                    .whereEqualTo("userId", installationId)
                    .get()
                    .await()

                val tasksArray = JSONArray()
                for (doc in tasks.documents) {
                    val taskJson = JSONObject().apply {
                        put("id", doc.id)
                        put("title", doc.getString("title"))
                        put("description", doc.getString("description"))
                        put("dueDate", doc.getLong("dueDate"))
                        put("priority", doc.getString("priority"))
                        put("category", doc.getString("category"))
                        put("completed", doc.getBoolean("completed") ?: false)
                    }
                    tasksArray.put(taskJson)
                }

                val backupData = tasksArray.toString().toByteArray()
                data.writeEntityHeader(TASKS_BACKUP_KEY, backupData.size)
                data.writeEntityData(backupData, backupData.size)

                // Write the new state
                DataOutputStream(FileOutputStream(newState.fileDescriptor)).use { out ->
                    out.writeLong(System.currentTimeMillis())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onRestore(
        data: BackupDataInput,
        appVersionCode: Int,
        newState: ParcelFileDescriptor?
    ) {
        // Restore Firestore data
        runBlocking {
            try {
                while (data.readNextHeader()) {
                    val key = data.key
                    val dataSize = data.dataSize

                    if (key == TASKS_BACKUP_KEY) {
                        val backupData = ByteArray(dataSize)
                        data.readEntityData(backupData, 0, dataSize)
                        
                        val tasksArray = JSONArray(String(backupData))
                        val db = FirebaseFirestore.getInstance()
                        val batch = db.batch()

                        for (i in 0 until tasksArray.length()) {
                            val taskJson = tasksArray.getJSONObject(i)
                            val taskRef = db.collection("tasks").document(taskJson.getString("id"))
                            
                            batch.set(taskRef, mapOf(
                                "title" to taskJson.getString("title"),
                                "description" to taskJson.getString("description"),
                                "dueDate" to taskJson.getLong("dueDate"),
                                "priority" to taskJson.getString("priority"),
                                "category" to taskJson.getString("category"),
                                "completed" to taskJson.getBoolean("completed"),
                                "userId" to installationId
                            ))
                        }

                        batch.commit().await()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
} 