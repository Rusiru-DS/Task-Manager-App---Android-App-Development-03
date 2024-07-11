package com.example.taskmanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.taskmanagerapp.databinding.ActivityUpdateTaskSectionBinding

// Activity class to update existing tasks.
class UpdateTaskSection : AppCompatActivity() {

    // Late-initializing properties for view binding and database helper.
    private lateinit var binding: ActivityUpdateTaskSectionBinding
    private lateinit var db: TaskDatabaseHelper
    private var taskId: Int = -1  // Variable to store task ID passed from another activity.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setting up view binding for the activity.
        binding = ActivityUpdateTaskSectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing the database helper.
        db = TaskDatabaseHelper(this)

        // Receiving the task ID passed from the previous activity. Default is -1 if not found.
        taskId = intent.getIntExtra("task_id", -1)
        if(taskId == -1){
            // If no task ID was passed, close the activity and return to the previous screen.
            finish()
            return
        }

        // Fetching the task details from the database using the task ID.
        val task = db.getTaskByID(taskId)
        // Setting the fetched task details to the corresponding input fields for editing.
        binding.updateTitleEditText.setText(task.title)
        binding.updateContentEditText.setText(task.content)
        binding.updateDeadlineEditText.setText(task.deadline)
        binding.updatePriorityEditText.setText(task.priority)

        // Setup for the save button to update the task.
        binding.updateSaveButton.setOnClickListener {
            // Gathering updated task details from the user input.
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val newDeadline = binding.updateDeadlineEditText.text.toString()
            val newPriority = binding.updatePriorityEditText.text.toString()

            // Creating a Task object with updated values.
            val updateTask = Task(taskId, newTitle, newContent, newDeadline, newPriority)
            // Updating the task in the database.
            db.updateTask(updateTask)
            // Close the activity after saving the changes.
            finish()
            // Show a confirmation toast message.
            Toast.makeText(this, "Task Updated Successfully", Toast.LENGTH_SHORT).show()
        }

        // Setup for the back button to return to the main activity.
        val btnNext = findViewById<ImageView>(R.id.gobackBTN)
        btnNext.setOnClickListener {
            val nextPage = Intent(this, MainActivity::class.java)
            startActivity(nextPage)
            finish()
        }
    }
}
