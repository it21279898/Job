package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CvApplicationHome : AppCompatActivity() {

    private lateinit var btnCvApply: Button
    private lateinit var btnCvView: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cv_application_home)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnCvApply = findViewById(R.id.btnCvApply)
        btnCvView = findViewById(R.id.btnCvView)

        btnCvApply.setOnClickListener {
            val intent = Intent(this,cvApplication::class.java)
            startActivity(intent)
        }
        btnCvView.setOnClickListener {
            val intent = Intent(this, CvFetchData::class.java)
            startActivity(intent)
        }

    }
}