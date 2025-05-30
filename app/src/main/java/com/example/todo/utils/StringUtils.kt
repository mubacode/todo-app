package com.example.todo.utils

import android.content.Context
import android.util.Log
import android.util.LruCache
import com.example.todo.R
import com.example.todo.data.Category
import com.example.todo.data.Priority

object StringUtils {
    private const val TAG = "StringUtils"
    private const val CACHE_SIZE = 100

    private val stringCache = LruCache<String, String>(CACHE_SIZE)

    fun getPriorityString(context: Context, priority: Priority): String {
        val cacheKey = "priority_${priority.name}"
        return stringCache.get(cacheKey) ?: try {
            val resourceId = when (priority) {
                Priority.HIGH -> R.string.priority_high
                Priority.MEDIUM -> R.string.priority_medium
                Priority.LOW -> R.string.priority_low
            }
            context.getString(resourceId).also {
                stringCache.put(cacheKey, it)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting priority string for $priority", e)
            priority.name
        }
    }

    fun getCategoryString(context: Context, category: Category): String {
        val cacheKey = "category_${category.name}"
        return stringCache.get(cacheKey) ?: try {
            val resourceId = when (category) {
                Category.PERSONAL -> R.string.category_personal
                Category.WORK -> R.string.category_work
                Category.SHOPPING -> R.string.category_shopping
                Category.HEALTH -> R.string.category_health
                Category.EDUCATION -> R.string.category_education
                Category.OTHER -> R.string.category_other
            }
            context.getString(resourceId).also {
                stringCache.put(cacheKey, it)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting category string for $category", e)
            category.name
        }
    }

    fun clearCache() {
        stringCache.evictAll()
    }
} 