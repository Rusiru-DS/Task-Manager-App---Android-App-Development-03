package com.example.taskmanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.taskmanagerapp.databinding.ActivityAddTaskSectionBinding

class AddTaskSection : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskSectionBinding
    private lateinit var db: TaskDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskSectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString().trim()
            val content = binding.contentEditText.text.toString().trim()
            val deadline = binding.deadlineEditText.text.toString().trim()
            val priority = binding.priorityEditText.text.toString().trim()

            if (title.isEmpty() || content.isEmpty() || deadline.isEmpty() || priority.isEmpty()) {
                // Show error message if any field is empty.
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_LONG).show()
            } else {
                // All fields are filled, proceed to create a new task.
                val task = Task(0, title, content, deadline, priority)
                db.insertTask(task)
                finish()
                Toast.makeText(this, "Task Added Successfully", Toast.LENGTH_SHORT).show()
            }
        }

        val btnNext = findViewById<ImageView>(R.id.gobackBTN)
        btnNext.setOnClickListener {
            val nextPage = Intent(this, HomeScreen::class.java)
            startActivity(nextPage)
            finish()
        }
    }
}
