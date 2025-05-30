package com.example.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.Task
import com.example.todo.data.Priority
import com.example.todo.data.Category
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val database = TaskDatabase.getDatabase(application)
    private val taskDao = database.taskDao()
    private val repository = TaskRepository(taskDao, application)
    
    private val _currentFilter = MutableStateFlow(TaskFilter.ALL)
    private val _searchQuery = MutableStateFlow("")
    private val _syncStatus = MutableStateFlow<SyncStatus>(SyncStatus.Idle)
    
    val syncStatus: StateFlow<SyncStatus> = _syncStatus.asStateFlow()
    
    val tasks: StateFlow<List<Task>> = combine(
        taskDao.getAllTasks(),
        _currentFilter,
        _searchQuery
    ) { tasks, filter, query ->
        tasks.filter { task ->
            val matchesQuery = task.title.contains(query, ignoreCase = true) ||
                    task.description.contains(query, ignoreCase = true)
            
            val matchesFilter = when (filter) {
                TaskFilter.ALL -> true
                TaskFilter.ACTIVE -> !task.isCompleted
                TaskFilter.COMPLETED -> task.isCompleted
                TaskFilter.HIGH_PRIORITY -> task.priority == Priority.HIGH
                TaskFilter.MEDIUM_PRIORITY -> task.priority == Priority.MEDIUM
                TaskFilter.LOW_PRIORITY -> task.priority == Priority.LOW
                TaskFilter.DUE_TODAY -> {
                    val today = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }.time
                    
                    task.dueDate?.let { dueDate ->
                        val taskDate = Calendar.getInstance().apply {
                            time = dueDate
                            set(Calendar.HOUR_OF_DAY, 0)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }.time
                        taskDate == today
                    } ?: false
                }
                TaskFilter.OVERDUE -> {
                    val now = Calendar.getInstance().time
                    task.dueDate?.let { dueDate ->
                        !task.isCompleted && dueDate.before(now)
                    } ?: false
                }
            }
            
            matchesQuery && matchesFilter
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private var currentFilter = TaskFilter.ALL
    private var currentSearch = ""
    
    fun refreshTasks() {
        // Trigger a refresh by temporarily changing and restoring the filter
        val tempFilter = currentFilter
        setFilter(TaskFilter.ALL)
        setFilter(tempFilter)
    }
    
    fun setFilter(filter: TaskFilter) {
        currentFilter = filter
        _currentFilter.value = filter
    }
    
    fun searchTasks(query: String) {
        currentSearch = query
        _searchQuery.value = query
    }

    suspend fun addTask(task: Task) {
        repository.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        repository.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        repository.deleteTask(task)
    }

    fun syncWithCloud() {
        viewModelScope.launch {
            _syncStatus.value = SyncStatus.Syncing
            try {
                repository.syncWithCloud()
                _syncStatus.value = SyncStatus.Success
            } catch (e: Exception) {
                _syncStatus.value = SyncStatus.Error(e.message ?: "Sync failed")
            }
        }
    }
}

sealed class SyncStatus {
    object Idle : SyncStatus()
    object Syncing : SyncStatus()
    object Success : SyncStatus()
    data class Error(val message: String) : SyncStatus()
} 