package com.example.taskmanagerapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

// Adapter class for RecyclerView to display tasks, inheriting from RecyclerView.Adapter.
class TasksAdapter(private var tasks: List<Task>, context: Context) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    // Database helper instance for database operations.
    private val db: TaskDatabaseHelper = TaskDatabaseHelper(context)

    // ViewHolder class that holds references to all views within a single list item.
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Views that display the task's title, content, deadline, and priority.
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val deadlineTextView: TextView = itemView.findViewById(R.id.deadlineTextView)
        val priorityTextView: TextView = itemView.findViewById(R.id.priorityTextView)

        // Buttons for updating and deleting a task.
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    // Inflates the task item layout and returns a ViewHolder with the layout.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    // Returns the number of items in the tasks list.
    override fun getItemCount(): Int = tasks.size

    // Binds data to each item of the RecyclerView.
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.titleTextView.text = task.title
        holder.contentTextView.text = task.content
        holder.deadlineTextView.text = task.deadline
        holder.priorityTextView.text = task.priority

        // Set up the update button to open the UpdateTaskSection with the task ID as an extra.
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateTaskSection::class.java).apply {
                putExtra("task_id", task.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        // Set up the delete button to remove the task from the database and update the displayed list.
        holder.deleteButton.setOnClickListener {
            db.deleteTask(task.id)
            refreshData(db.getAllTasks())
            Toast.makeText(holder.itemView.context, "Task Deleted Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    // Updates the tasks list and notifies the adapter of the data change.
    fun refreshData(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
