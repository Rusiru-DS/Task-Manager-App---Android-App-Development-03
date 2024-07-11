package com.example.taskmanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Properties for binding, database helper, and the adapter for the RecyclerView.
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TaskDatabaseHelper
    private lateinit var tasksAdapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout for this activity using View Binding.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the database helper.
        db = TaskDatabaseHelper(this)

        // Initialize the adapter for the RecyclerView with all tasks from the database.
        tasksAdapter = TasksAdapter(db.getAllTasks(), this)

        // Set up the RecyclerView with a LinearLayoutManager and attach the adapter.
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.taskRecyclerView.adapter = tasksAdapter

        // Set an OnClickListener for the "Add" button to start the AddTaskSection activity.
        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddTaskSection::class.java)
            startActivity(intent)
        }

        // Find the ImageView acting as a button to go back to the home screen and set an OnClickListener.
        val btnNext = findViewById<ImageView>(R.id.back2homeBtn)
        btnNext.setOnClickListener {
            val nextPage = Intent(this, HomeScreen::class.java)
            startActivity(nextPage)
            finish()
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.priority_levels,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.prioritySpinner.adapter = adapter
        }
        binding.prioritySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedPriority = parent.getItemAtPosition(position) as String
                filterTasksByPriority(selectedPriority)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optional: Handle the case where no selection is made
            }
        }


        // Add a TextWatcher to the search EditText to listen for text changes and filter tasks.
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // beginning at start are about to be replaced by new text with length after.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // beginning at start have just replaced old text that had length before.
            }

            override fun afterTextChanged(s: Editable) {
                // This method is called to notify you that, somewhere within s, the text has been changed.
                // It is a good place to filter tasks based on the search text.
                filterTasks(s.toString())
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // Refresh the data in the adapter with all tasks from the database when the activity resumes.
        tasksAdapter.refreshData(db.getAllTasks())
    }

    private fun filterTasks(text: String) {
        // Filter the tasks from the database based on the text input in the search bar.
        // Tasks are matched if any of their properties (title, content, deadline, priority) contain the text.
        val filteredTasks = db.getAllTasks().filter {
            it.title.contains(text, ignoreCase = true) ||
                    it.content.contains(text, ignoreCase = true) ||
                    it.deadline.contains(text, ignoreCase = true) ||
                    it.priority.contains(text, ignoreCase = true)
        }
        // Refresh the adapter with the filtered tasks.
        tasksAdapter.refreshData(filteredTasks)
    }

    private fun filterTasksByPriority(priority: String) {
        val filteredTasks = if (priority == "All") {
            db.getAllTasks() // If "All" is selected, show all tasks.
        } else {
            db.getAllTasks().filter { it.priority == priority }
        }
        tasksAdapter.refreshData(filteredTasks)
    }
}
