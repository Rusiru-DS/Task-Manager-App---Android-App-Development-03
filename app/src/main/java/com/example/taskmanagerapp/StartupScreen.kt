package com.example.taskmanagerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartupScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup_screen)

        //get the component with id - go to homepage
        val btnNext = findViewById<Button>(R.id.startupBtn)

        btnNext.setOnClickListener {
            val nextPage = Intent(this, HomeScreen::class.java);
            startActivity(nextPage);
            finish();
        }
    }
}