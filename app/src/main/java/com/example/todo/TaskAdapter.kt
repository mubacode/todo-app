package com.example.todo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.Task
import com.example.todo.data.Priority
import com.example.todo.data.Category
import com.example.todo.utils.DateTimeUtils
import com.example.todo.utils.StringUtils
import com.example.todo.utils.TranslationManager
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import java.util.*

class TaskAdapter(
    private val onTaskChecked: (Task, Boolean) -> Unit,
    private val onEditClick: (Task) -> Unit,
    private val onDeleteClick: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    private var currentLanguage = "en"
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val activeJobs = mutableMapOf<Int, Job>()

    fun updateLanguage(newLanguage: String) {
        currentLanguage = newLanguage
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(WeakReference(view))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, position)
    }

    override fun onViewRecycled(holder: TaskViewHolder) {
        super.onViewRecycled(holder)
        // Cancel any ongoing translation job for this position
        activeJobs[holder.bindingAdapterPosition]?.cancel()
        activeJobs.remove(holder.bindingAdapterPosition)
        holder.cleanup()
    }

    fun cleanup() {
        scope.cancel()
        activeJobs.clear()
    }

    inner class TaskViewHolder(private val itemViewRef: WeakReference<View>) : RecyclerView.ViewHolder(itemViewRef.get()!!) {
        private var checkBox: CheckBox? = null
        private var titleText: TextView? = null
        private var descriptionText: TextView? = null
        private var dueDateText: TextView? = null
        private var reminderTimeText: TextView? = null
        private var clockIcon: View? = null
        private var editButton: ImageButton? = null
        private var deleteButton: ImageButton? = null
        private var priorityIndicator: View? = null

        init {
            itemViewRef.get()?.let { view ->
                checkBox = view.findViewById(R.id.checkBox)
                titleText = view.findViewById(R.id.textTitle)
                descriptionText = view.findViewById(R.id.textDescription)
                dueDateText = view.findViewById(R.id.textDueDate)
                reminderTimeText = view.findViewById(R.id.textReminderTime)
                clockIcon = view.findViewById(R.id.clockIcon)
                editButton = view.findViewById(R.id.buttonEdit)
                deleteButton = view.findViewById(R.id.buttonDelete)
                priorityIndicator = view.findViewById(R.id.priorityIndicator)
                
                // Ensure the reminder time view is visible by default
                reminderTimeText?.visibility = View.VISIBLE
                clockIcon?.visibility = View.VISIBLE
            }
        }

        fun bind(task: Task, position: Int) {
            itemViewRef.get()?.let { itemView ->
                checkBox?.isChecked = task.isCompleted
                titleText?.text = task.title
                descriptionText?.text = task.description

                // Handle due date display and overdue status
                task.dueDate?.let { dueDate ->
                    val now = Calendar.getInstance().time
                    val isOverdue = !task.isCompleted && dueDate.before(now)
                    
                    dueDateText?.apply {
                        text = itemView.context.getString(R.string.task_due, DateTimeUtils.formatDate(dueDate))
                        setTextColor(ContextCompat.getColor(itemView.context, 
                            if (isOverdue) R.color.overdue_text else android.R.color.white))
                    }
                } ?: run {
                    dueDateText?.text = ""
                }

                // Handle reminder time display
                if (task.reminderTime != null) {
                    val formattedTime = DateTimeUtils.formatTime(task.reminderTime)
                    Log.d("TaskAdapter", "Task ${task.id}: Setting reminder time to $formattedTime")
                    
                    clockIcon?.visibility = View.VISIBLE
                    reminderTimeText?.visibility = View.VISIBLE
                    
                    reminderTimeText?.post {
                        reminderTimeText?.text = formattedTime
                        reminderTimeText?.invalidate()
                    }
                } else {
                    Log.d("TaskAdapter", "Task ${task.id}: No reminder time")
                    clockIcon?.visibility = View.GONE
                    reminderTimeText?.visibility = View.GONE
                }

                val color = when (task.priority) {
                    Priority.HIGH -> R.color.priority_high
                    Priority.MEDIUM -> R.color.priority_medium
                    Priority.LOW -> R.color.priority_low
                }
                priorityIndicator?.setBackgroundColor(ContextCompat.getColor(itemView.context, color))

                checkBox?.setOnCheckedChangeListener { _, isChecked ->
                    onTaskChecked(task, isChecked)
                }

                editButton?.setOnClickListener {
                    onEditClick(task)
                }

                deleteButton?.setOnClickListener {
                    onDeleteClick(task)
                }
            }
        }

        fun cleanup() {
            checkBox?.setOnCheckedChangeListener(null)
            editButton?.setOnClickListener(null)
            deleteButton?.setOnClickListener(null)
            
            // Clear references
            checkBox = null
            titleText = null
            descriptionText = null
            dueDateText = null
            reminderTimeText = null
            clockIcon = null
            editButton = null
            deleteButton = null
            priorityIndicator = null
        }
    }

    private class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
} 