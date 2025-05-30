package com.example.todo.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.todo.data.Task
import java.util.*

class ReminderManager(private val context: Context) {
    
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    
    fun scheduleReminder(task: Task) {
        task.reminderTime?.let { reminderTime ->
            val now = Calendar.getInstance().time
            Log.d("ReminderManager", "Current time: $now")
            Log.d("ReminderManager", "Reminder time: $reminderTime")
            
            // Only schedule if the reminder time is in the future
            if (reminderTime.after(now)) {
                val intent = Intent(context, ReminderReceiver::class.java).apply {
                    putExtra(ReminderReceiver.EXTRA_TASK_ID, task.id)
                    putExtra(ReminderReceiver.EXTRA_TASK_TITLE, task.title)
                }
                
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    task.id.toInt(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            reminderTime.time,
                            pendingIntent
                        )
                        Log.d("ReminderManager", "Scheduled reminder for task ${task.id} at $reminderTime")
                    } else {
                        alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            reminderTime.time,
                            pendingIntent
                        )
                        Log.d("ReminderManager", "Scheduled reminder (pre-M) for task ${task.id} at $reminderTime")
                    }
                } catch (e: Exception) {
                    Log.e("ReminderManager", "Failed to schedule reminder", e)
                }
            } else {
                Log.w("ReminderManager", "Reminder time is in the past, not scheduling: $reminderTime")
            }
        } ?: Log.w("ReminderManager", "No reminder time set for task ${task.id}")
    }
    
    fun cancelReminder(task: Task) {
        try {
            val intent = Intent(context, ReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                task.id.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
            Log.d("ReminderManager", "Cancelled reminder for task ${task.id}")
        } catch (e: Exception) {
            Log.e("ReminderManager", "Failed to cancel reminder", e)
        }
    }
} 