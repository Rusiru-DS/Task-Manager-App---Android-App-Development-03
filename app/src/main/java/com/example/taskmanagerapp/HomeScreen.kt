package com.example.taskmanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val btnNext = findViewById<ImageView>(R.id.goViewbtn)

        btnNext.setOnClickListener {
            val nextPage = Intent(this, MainActivity::class.java);
            startActivity(nextPage);
            finish();
        }

        val btnNext2 = findViewById<ImageView>(R.id.goViewbtn2)

        btnNext2.setOnClickListener {
            val nextPage = Intent(this, MainActivity::class.java);
            startActivity(nextPage);
            finish();
        }

        val btnNext3 = findViewById<ImageView>(R.id.goAddTaskbtn)

        btnNext3.setOnClickListener {
            val nextPage = Intent(this, AddTaskSection::class.java);
            startActivity(nextPage);
            finish();
        }

        val btnNext4 = findViewById<ImageView>(R.id.goAddTaskbtn2)

        btnNext4.setOnClickListener {
            val nextPage = Intent(this, AddTaskSection::class.java);
            startActivity(nextPage);
            finish();
        }

        val btnNext5 = findViewById<ImageView>(R.id.goCalendarbtn2)

        btnNext5.setOnClickListener {
            val nextPage = Intent(this, CalendarScreen::class.java);
            startActivity(nextPage);
            finish();
        }

        val btnNext6 = findViewById<ImageView>(R.id.goCalendarbtn)

        btnNext6.setOnClickListener {
            val nextPage = Intent(this, CalendarScreen::class.java);
            startActivity(nextPage);
            finish();
        }

        val btnNext7 = findViewById<ImageView>(R.id.logoutBtn)

        btnNext7.setOnClickListener {
            val nextPage = Intent(this, StartupScreen::class.java);
            startActivity(nextPage);
            finish();
        }
    }
}