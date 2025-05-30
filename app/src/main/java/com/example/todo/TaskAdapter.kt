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
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

    override fun onViewRecycled(holder: TaskViewHolder) {
        super.onViewRecycled(holder)
        activeJobs[holder.bindingAdapterPosition]?.cancel()
        activeJobs.remove(holder.bindingAdapterPosition)
    }

    fun cleanup() {
        scope.cancel()
        activeJobs.clear()
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        private val titleText: TextView = itemView.findViewById(R.id.textTitle)
        private val descriptionText: TextView = itemView.findViewById(R.id.textDescription)
        private val dueDateText: TextView = itemView.findViewById(R.id.textDueDate)
        private val reminderTimeText: TextView = itemView.findViewById(R.id.textReminderTime)
        private val clockIcon: View = itemView.findViewById(R.id.clockIcon)
        private val editButton: ImageButton = itemView.findViewById(R.id.buttonEdit)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.buttonDelete)
        private val priorityIndicator: View = itemView.findViewById(R.id.priorityIndicator)
        
        init {
            // Set click listeners once in init
            editButton.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onEditClick(getItem(position))
                }
            }
            
            deleteButton.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteClick(getItem(position))
                }
            }
            
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onTaskChecked(getItem(position), isChecked)
                }
            }
        }

        fun bind(task: Task) {
            checkBox.isChecked = task.isCompleted
            titleText.text = task.title
            descriptionText.text = task.description

            // Handle due date display and overdue status
            if (task.dueDate != null) {
                val now = Calendar.getInstance().time
                val isOverdue = !task.isCompleted && task.dueDate.before(now)
                
                dueDateText.apply {
                    text = context.getString(R.string.task_due, DateTimeUtils.formatDate(task.dueDate))
                    setTextColor(ContextCompat.getColor(context, 
                        if (isOverdue) R.color.overdue_text else android.R.color.white))
                    visibility = View.VISIBLE
                }
            } else {
                dueDateText.visibility = View.GONE
            }

            // Handle reminder time display
            if (task.reminderTime != null) {
                val formattedTime = DateTimeUtils.formatTime(task.reminderTime)
                Log.d("TaskAdapter", "Task ${task.id}: Setting reminder time to $formattedTime")
                
                clockIcon.visibility = View.VISIBLE
                reminderTimeText.visibility = View.VISIBLE
                reminderTimeText.text = formattedTime
            } else {
                Log.d("TaskAdapter", "Task ${task.id}: No reminder time")
                clockIcon.visibility = View.GONE
                reminderTimeText.visibility = View.GONE
            }

            val color = when (task.priority) {
                Priority.HIGH -> R.color.priority_high
                Priority.MEDIUM -> R.color.priority_medium
                Priority.LOW -> R.color.priority_low
            }
            priorityIndicator.setBackgroundColor(ContextCompat.getColor(itemView.context, color))
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