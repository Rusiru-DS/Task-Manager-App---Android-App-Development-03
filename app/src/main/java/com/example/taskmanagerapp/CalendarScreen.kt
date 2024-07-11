package com.example.taskmanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class CalendarScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_screen)

        val btnNext = findViewById<ImageView>(R.id.gobackBTN)

        btnNext.setOnClickListener {
            val nextPage = Intent(this, MainActivity::class.java);
            startActivity(nextPage);
            finish();
        }

        val btnNext1 = findViewById<Button>(R.id.addTaskBtn)

        btnNext1.setOnClickListener {
            val nextPage = Intent(this, AddTaskSection::class.java);
            startActivity(nextPage);
            finish();
        }

        val btnNext4 = findViewById<ImageView>(R.id.gobackBTN)

        btnNext4.setOnClickListener {
            val nextPage = Intent(this, HomeScreen::class.java);
            startActivity(nextPage);
            finish();
        }

    }
}