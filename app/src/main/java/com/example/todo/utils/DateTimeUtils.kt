package com.example.todo.utils

import android.content.Context
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {
    private var currentLocale: Locale = Locale.getDefault()
    private var dateFormat: SimpleDateFormat? = null
    private var timeFormat: SimpleDateFormat? = null
    private const val DATE_PATTERN = "MMM dd, yyyy"
    private const val TIME_PATTERN = "HH:mm"
    private const val TAG = "DateTimeUtils"
    
    fun updateLocale(locale: Locale) {
        currentLocale = locale
        // Clear cached formatters so they'll be recreated with new locale
        dateFormat = null
        timeFormat = null
    }
    
    private fun getDateFormat(): SimpleDateFormat {
        return dateFormat ?: SimpleDateFormat("dd MMMM yyyy", currentLocale).also {
            dateFormat = it
        }
    }
    
    private fun getTimeFormat(): SimpleDateFormat {
        return timeFormat ?: SimpleDateFormat("HH:mm", currentLocale).also {
            timeFormat = it
        }
    }
    
    fun formatDate(date: Date): String {
        return getDateFormat().format(date)
    }
    
    fun formatTime(time: Date): String {
        return getTimeFormat().format(time)
    }
    
    fun parseDate(dateStr: String): Date? {
        return try {
            getDateFormat().parse(dateStr)
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing date: $dateStr", e)
            null
        }
    }
    
    fun parseTime(timeStr: String): Date? {
        return try {
            getTimeFormat().parse(timeStr)
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing time: $timeStr", e)
            null
        }
    }
} 