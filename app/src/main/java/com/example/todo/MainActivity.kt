package com.example.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.Task
import com.example.todo.data.Priority
import com.example.todo.data.Category
import com.example.todo.notification.ReminderManager
import com.example.todo.utils.DateTimeUtils
import com.example.todo.utils.LanguageManager
import com.example.todo.utils.StringUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var searchEditText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var languageSpinner: Spinner
    private lateinit var reminderManager: ReminderManager

    private val languageCodes = listOf("en", "tr", "pl", "de", "fr", "es", "it")
    private val languageNames = listOf(
        R.string.language_english,
        R.string.language_turkish,
        R.string.language_polish,
        R.string.language_german,
        R.string.language_french,
        R.string.language_spanish,
        R.string.language_italian
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reminderManager = ReminderManager(this)

        initializeViews()
        setupLanguageSpinner()
        setupRecyclerView()
        setupViewModel()
        setupSearchBar()
        setupAddButton()

        // Clear string cache when low on memory
        StringUtils.clearCache()
    }

    private fun initializeViews() {
        searchEditText = findViewById(R.id.searchBar)
        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.fabAddTask)
        languageSpinner = findViewById(R.id.spinnerLanguage)
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(
            onTaskChecked = { task, isChecked ->
                lifecycleScope.launch {
                    taskViewModel.updateTask(task.copy(isCompleted = isChecked))
                }
            },
            onEditClick = { task -> showTaskDialog(task) },
            onDeleteClick = { task -> showDeleteConfirmation(task) }
        )
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }
    }

    private fun setupViewModel() {
        taskViewModel = TaskViewModel(application)
        lifecycleScope.launch {
            // Only collect when the lifecycle is at least STARTED
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                taskViewModel.tasks.collectLatest { tasks ->
                    taskAdapter.submitList(tasks)
                }
            }
        }
    }

    private fun setupSearchBar() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                taskViewModel.searchTasks(s?.toString() ?: "")
            }
        })
    }

    private fun setupAddButton() {
        addButton.setOnClickListener {
            showTaskDialog()
        }
    }

    private fun showTaskDialog(task: Task? = null) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_task, null)
        
        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val descriptionEditText = dialogView.findViewById<EditText>(R.id.editTextDescription)
        val dueDateEditText = dialogView.findViewById<EditText>(R.id.editTextDueDate)
        val reminderTimeEditText = dialogView.findViewById<EditText>(R.id.reminderTimeEditText)
        val prioritySpinner = dialogView.findViewById<Spinner>(R.id.spinnerPriority)
        val categorySpinner = dialogView.findViewById<Spinner>(R.id.spinnerCategory)

        if (!validateDialogViews(titleEditText, descriptionEditText, dueDateEditText, 
            reminderTimeEditText, prioritySpinner, categorySpinner)) {
            return
        }

        setupSpinners(prioritySpinner, categorySpinner)
        populateExistingData(task, titleEditText, descriptionEditText, dueDateEditText, 
            reminderTimeEditText, prioritySpinner, categorySpinner)
        setupDateTimePickers(dueDateEditText, reminderTimeEditText)

        showDialog(task, dialogView, titleEditText, descriptionEditText, dueDateEditText,
            reminderTimeEditText, prioritySpinner, categorySpinner)
    }

    private fun validateDialogViews(vararg views: View?): Boolean {
        val missingViews = views.filterIndexed { index, view -> 
            if (view == null) {
                Log.e("MainActivity", "View at index $index is null")
                true
            } else false
        }
        return missingViews.isEmpty()
    }

    private fun setupSpinners(prioritySpinner: Spinner, categorySpinner: Spinner) {
        val priorities = Priority.values()
        val categories = Category.values()

        prioritySpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            priorities.map { StringUtils.getPriorityString(this, it) }
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        categorySpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categories.map { StringUtils.getCategoryString(this, it) }
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    private fun populateExistingData(
        task: Task?,
        titleEditText: EditText,
        descriptionEditText: EditText,
        dueDateEditText: EditText,
        reminderTimeEditText: EditText,
        prioritySpinner: Spinner,
        categorySpinner: Spinner
    ) {
        task?.let {
            titleEditText.setText(it.title)
            descriptionEditText.setText(it.description)
            it.dueDate?.let { date -> dueDateEditText.setText(DateTimeUtils.formatDate(date)) }
            it.reminderTime?.let { time -> reminderTimeEditText.setText(DateTimeUtils.formatTime(time)) }
            prioritySpinner.setSelection(Priority.values().indexOf(it.priority))
            categorySpinner.setSelection(Category.values().indexOf(it.category))
        }
    }

    private fun setupDateTimePickers(dueDateEditText: EditText, reminderTimeEditText: EditText) {
        dueDateEditText.setOnClickListener {
            showDatePicker(dueDateEditText)
        }

        reminderTimeEditText.setOnClickListener {
            showTimePicker(reminderTimeEditText)
        }
    }

    private fun showDatePicker(dueDateEditText: EditText) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.set(year, month, day)
                dueDateEditText.setText(DateTimeUtils.formatDate(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePicker(reminderTimeEditText: EditText) {
        val calendar = Calendar.getInstance()
        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                Log.d("MainActivity", "Setting reminder time: ${calendar.time}")
                reminderTimeEditText.setText(DateTimeUtils.formatTime(calendar.time))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun showDialog(
        task: Task?,
        dialogView: View,
        titleEditText: EditText,
        descriptionEditText: EditText,
        dueDateEditText: EditText,
        reminderTimeEditText: EditText,
        prioritySpinner: Spinner,
        categorySpinner: Spinner
    ) {
        AlertDialog.Builder(this)
            .setTitle(if (task == null) getString(R.string.add_task) else getString(R.string.edit_task))
            .setView(dialogView)
            .setPositiveButton(getString(R.string.save)) { _, _ ->
                handleDialogSave(task, titleEditText, descriptionEditText, dueDateEditText,
                    reminderTimeEditText, prioritySpinner, categorySpinner)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun handleDialogSave(
        task: Task?,
        titleEditText: EditText,
        descriptionEditText: EditText,
        dueDateEditText: EditText,
        reminderTimeEditText: EditText,
        prioritySpinner: Spinner,
        categorySpinner: Spinner
    ) {
        val title = titleEditText.text.toString().trim()
        if (title.isEmpty()) {
            titleEditText.error = getString(R.string.title_required)
            return
        }

        val description = descriptionEditText.text.toString().trim()
        val dateStr = dueDateEditText.text.toString()
        val timeStr = reminderTimeEditText.text.toString()

        val dueDate = if (dateStr.isNotEmpty()) DateTimeUtils.parseDate(dateStr) else null
        val reminderTime = if (timeStr.isNotEmpty()) {
            val calendar = Calendar.getInstance()
            val timeParts = timeStr.split(":")
            if (timeParts.size == 2) {
                val hour = timeParts[0].toIntOrNull() ?: 0
                val minute = timeParts[1].toIntOrNull() ?: 0
                
                // If we have a due date, use it as the base for the reminder
                if (dueDate != null) {
                    calendar.time = dueDate
                }
                
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                
                val reminderDateTime = calendar.time
                Log.d("MainActivity", "Setting reminder time: $reminderDateTime")
                
                // Only return the reminder time if it's in the future
                if (reminderDateTime.after(Calendar.getInstance().time)) {
                    reminderDateTime
                } else {
                    Log.w("MainActivity", "Reminder time is in the past, ignoring: $reminderDateTime")
                    null
                }
            } else null
        } else null

        val priority = Priority.values()[prioritySpinner.selectedItemPosition]
        val category = Category.values()[categorySpinner.selectedItemPosition]

        val newTask = Task(
            id = task?.id ?: 0,
            title = title,
            description = description,
            dueDate = dueDate,
            reminderTime = reminderTime,
            priority = priority,
            category = category,
            isCompleted = task?.isCompleted ?: false,
            installationId = task?.installationId ?: ""
        )

        Log.d("MainActivity", "Saving task with reminder time: ${newTask.reminderTime}")

        lifecycleScope.launch {
            if (task == null) {
                taskViewModel.addTask(newTask)
                // Schedule reminder for new task
                if (newTask.reminderTime != null) {
                    reminderManager.scheduleReminder(newTask)
                }
            } else {
                taskViewModel.updateTask(newTask)
                // Update reminder for existing task
                if (task.reminderTime != null) {
                    reminderManager.cancelReminder(task)
                }
                if (newTask.reminderTime != null) {
                    reminderManager.scheduleReminder(newTask)
                }
            }
        }
    }

    private fun showDeleteConfirmation(task: Task) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_task))
            .setMessage(getString(R.string.delete_task_confirmation))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                lifecycleScope.launch {
                    taskViewModel.deleteTask(task)
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun setupLanguageSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            languageNames.map { getString(it) }
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        
        languageSpinner.adapter = adapter

        val currentLanguage = LanguageManager.getCurrentLanguage()
        languageCodes.indexOf(currentLanguage).takeIf { it != -1 }?.let {
            languageSpinner.setSelection(it)
        }

        languageSpinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = languageCodes[position]
                if (selectedLanguage != LanguageManager.getCurrentLanguage()) {
                    val newLocale = Locale(selectedLanguage)
                    val updatedContext = LanguageManager.updateLocale(this@MainActivity, selectedLanguage)
                    
                    // Update date/time utils with new locale
                    DateTimeUtils.updateLocale(newLocale)
                    
                    // Update configuration
                    resources.updateConfiguration(updatedContext.resources.configuration, resources.displayMetrics)
                    
                    // Update adapter with new language and force refresh
                    taskAdapter.updateLanguage(selectedLanguage)
                    
                    // Force refresh the task list to update date formats
                    lifecycleScope.launch {
                        taskViewModel.refreshTasks()
                    }
                    
                    // Recreate the activity to apply all changes
                    recreate()
                }
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_all -> {
                taskViewModel.setFilter(TaskFilter.ALL)
                true
            }
            R.id.filter_active -> {
                taskViewModel.setFilter(TaskFilter.ACTIVE)
                true
            }
            R.id.filter_completed -> {
                taskViewModel.setFilter(TaskFilter.COMPLETED)
                true
            }
            R.id.filter_high_priority -> {
                taskViewModel.setFilter(TaskFilter.HIGH_PRIORITY)
                true
            }
            R.id.filter_medium_priority -> {
                taskViewModel.setFilter(TaskFilter.MEDIUM_PRIORITY)
                true
            }
            R.id.filter_low_priority -> {
                taskViewModel.setFilter(TaskFilter.LOW_PRIORITY)
                true
            }
            R.id.filter_due_today -> {
                taskViewModel.setFilter(TaskFilter.DUE_TODAY)
                true
            }
            R.id.filter_overdue -> {
                taskViewModel.setFilter(TaskFilter.OVERDUE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        StringUtils.clearCache()
        com.example.todo.utils.TranslationManager.cleanupTranslators()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level >= android.content.ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
            StringUtils.clearCache()
        }
    }
} 