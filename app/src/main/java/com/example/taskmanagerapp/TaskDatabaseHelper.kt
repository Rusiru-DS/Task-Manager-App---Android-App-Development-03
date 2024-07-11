package com.example.taskmanagerapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// The TaskDatabaseHelper class extends SQLiteOpenHelper and provides database support for managing tasks.
class TaskDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Constants used within the database such as database name, version, and table/column names.
        private const val DATABASE_NAME = "taskmanagerapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "alltasks"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_DEADLINE = "deadline"
        private const val COLUMN_PRIORITY = "priority"
    }

    // onCreate is called to create the database table for the first time.
    override fun onCreate(db: SQLiteDatabase?) {
        // SQL statement to create a new table for storing tasks.
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT, $COLUMN_DEADLINE TEXT, $COLUMN_PRIORITY TEXT)"
        db?.execSQL(createTableQuery)
    }

    // onUpgrade is called when the database needs to be upgraded.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // SQL statement to drop the existing table and create a new one if the version changes.
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    // Function to insert a new task into the database.
    fun insertTask(task: Task) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, task.title)
            put(COLUMN_CONTENT, task.content)
            put(COLUMN_DEADLINE, task.deadline)
            put(COLUMN_PRIORITY, task.priority)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Function to retrieve all tasks from the database.
    fun getAllTasks(): List<Task> {
        val tasksList = mutableListOf<Task>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val deadline = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEADLINE))
            val priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))

            val task = Task(id, title, content, deadline, priority)
            tasksList.add(task)
        }
        cursor.close()
        db.close()
        return tasksList
    }

    // Function to update an existing task in the database.
    fun updateTask(task: Task) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, task.title)
            put(COLUMN_CONTENT, task.content)
            put(COLUMN_DEADLINE, task.deadline)
            put(COLUMN_PRIORITY, task.priority)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(task.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    // Function to retrieve a single task by its ID.
    fun getTaskByID(taskId: Int): Task {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $taskId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
        val deadline = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DEADLINE))
        val priority = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))

        cursor.close()
        db.close()
        return Task(id, title, content, deadline, priority)
    }

    // Function to delete a task from the database.
    fun deleteTask(taskId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(taskId.toString())

        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}
