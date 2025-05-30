package com.example.todo.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.todo.R

class ReminderReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getLongExtra(EXTRA_TASK_ID, -1)
        val taskTitle = intent.getStringExtra(EXTRA_TASK_TITLE) ?: ""
        
        Log.d("ReminderReceiver", "Received reminder for task $taskId: $taskTitle")
        showNotification(context, taskId, taskTitle)
    }
    
    private fun showNotification(context: Context, taskId: Long, taskTitle: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    context.getString(R.string.reminder),
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Task reminder notifications"
                    enableLights(true)
                    enableVibration(true)
                }
                notificationManager.createNotificationChannel(channel)
                Log.d("ReminderReceiver", "Created notification channel")
            } catch (e: Exception) {
                Log.e("ReminderReceiver", "Failed to create notification channel", e)
            }
        }
        
        try {
            // Build the notification
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(context.getString(R.string.reminder))
                .setContentText(taskTitle)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()
            
            // Show the notification
            notificationManager.notify(taskId.toInt(), notification)
            Log.d("ReminderReceiver", "Showed notification for task $taskId")
        } catch (e: Exception) {
            Log.e("ReminderReceiver", "Failed to show notification", e)
        }
    }
    
    companion object {
        const val CHANNEL_ID = "task_reminders"
        const val EXTRA_TASK_ID = "task_id"
        const val EXTRA_TASK_TITLE = "task_title"
    }
} 